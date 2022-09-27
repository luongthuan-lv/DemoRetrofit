package com.luongthuan.demoretrofit.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.luongthuan.demoretrofit.MyApplication
import com.luongthuan.demoretrofit.util.Constants
import com.luongthuan.demoretrofit.util.Constants.CACHE_CONTROL
import com.luongthuan.demoretrofit.util.Constants.HEADER_PRAGMA
import com.luongthuan.demoretrofit.util.Constants.TIME_CACHE_OFFLINE
import com.luongthuan.demoretrofit.util.Constants.TIME_CACHE_ONLINE
import okhttp3.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by Luong Thuan on 15/08/2022.
 */
object RetrofitInstance {

    /*Add Custom Request Headers*/
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    /*----------------------------*/

//    val myCache = Cache(MyApplication().getInstance()!!.cacheDir, CACHE_SIZE)

//    /*Add Caching Request*/
//    val cachingOkHttpClient = OkHttpClient.Builder()
//        .cache(myCache)
//        .addInterceptor { chain ->
//            var request = chain.request()
//            request = if (MyApplication().hasNetwork())
//                request.newBuilder().header(
//                    CACHE_CONTROL, TIME_CACHE_ONLINE
//                ).build()
//            else
//                request.newBuilder().header(
//                    CACHE_CONTROL, TIME_CACHE_OFFLINE
//                ).build()
//            chain.proceed(request)
//        }
//        .build()
//
//    /*-------------------------------*/


    val myCache =
        Cache(File(MyApplication.instance.cacheDir, "someIdentifier"), Constants.CACHE_SIZE)

    fun hasNetwork(): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager =
            MyApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    //    /*Add Caching Request*/
    val cachingOkHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (hasNetwork()!!)
                request.newBuilder().header(
                    CACHE_CONTROL, TIME_CACHE_ONLINE
                ).build()
            else
                request.newBuilder().header(
                    CACHE_CONTROL, TIME_CACHE_OFFLINE
                ).build()
            chain.proceed(request)
        }
        .build()


//    val okHttpClient = OkHttpClient.Builder()
//        // Specify the cache we created earlier.
//        .cache(myCache)
//        // Add an Interceptor to the OkHttpClient.
//        .addInterceptor { chain ->
//
//            // Get the request from the chain.
//            var request = chain.request()
//
//            /*
//            *  Leveraging the advantage of using Kotlin,
//            *  we initialize the request and change its header depending on whether
//            *  the device is connected to Internet or not.
//            */
//            request = if (hasNetwork()!!)
//            /*
//            *  If there is Internet, get the cache that was stored 5 seconds ago.
//            *  If the cache is older than 5 seconds, then discard it,
//            *  and indicate an error in fetching the response.
//            *  The 'max-age' attribute is responsible for this behavior.
//            */
//                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
//            else
//            /*
//            *  If there is no Internet, get the cache that was stored 7 days ago.
//            *  If the cache is older than 7 days, then discard it,
//            *  and indicate an error in fetching the response.
//            *  The 'max-stale' attribute is responsible for this behavior.
//            *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
//            */
//                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
//            // End of if-else statement
//
//            // Add the modified request to the chain.
//            chain.proceed(request)
//        }
//        .build()


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            //add client
            //.client(client)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
            .addNetworkInterceptor(networkInterceptor()) // only used when network is on
            .addInterceptor(offlineInterceptor())
            .build()
    }


    /**
     * This interceptor will be called both if the network is available and if the network is not available
     * @return
     */
    private fun offlineInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d("thuanoc", "offline interceptor: called.")
                var request: Request = chain.request()

                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!hasNetwork()!!) {
                    val cacheControl: CacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }
                return chain.proceed(request)
            }
        }
    }


    /**
     * This interceptor will be called ONLY if the network is available
     * @return
     */
    fun networkInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d("thuanoc", "network interceptor: called.")
                val response: Response = chain.proceed(chain.request())
                val cacheControl: CacheControl = CacheControl.Builder()
                    .maxAge(15, TimeUnit.SECONDS)
                    .build()
                return response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(CACHE_CONTROL)
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build()
            }
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d("thuanoc", "log: http log: $message")
                }
            })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    /*----------------*/
    var onlineInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
        }
    }

    var offlineInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            if (!hasNetwork()!!) {
                val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            return chain.proceed(request)
        }
    }


    var okHttpClient: OkHttpClient =
        OkHttpClient.Builder() // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(myCache)
            .build()


}