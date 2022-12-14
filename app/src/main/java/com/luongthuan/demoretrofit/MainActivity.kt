package com.luongthuan.demoretrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.luongthuan.demoretrofit.api.RetrofitInstance
import com.luongthuan.demoretrofit.models.Photo
import com.luongthuan.demoretrofit.respository.Repository
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Cache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        /*Send a simple GET request*/
//        viewModel.getPost("121212")
//        viewModel.myResponse.observe(this, Observer { response ->
//            Log.i("thuanoc", "ADD HEADER:  ${response.headers().get("Auth").toString()}")
//        })



//        viewModel.getPost()
//        viewModel.myResponse.observe(this, Observer { response ->
//            Log.i("thuanoc", response.body().toString())
//        })


//        viewModel.run {
//            getPostNumber(edtNumber.text.toString().toInt())
//            myResponseNumber.observe(this@MainActivity, Observer { response ->
//                tvData.text = response.body.toString()
//            })
//        }


//        viewModel.run {
//            getCustomPost(edtNumber.text.toString().toInt(), "id", "desc")
//            myResponseUserId.observe(this@MainActivity, Observer { response ->
//                if (response.isSuccessful) {
//                    tvData.text = response.body().toString()
//                    response.body()?.forEach {
//                        Log.i(
//                            "thuanoc",
//                            "${it.userId.toString()} \n ${it.id.toString()} \n ${it.title.toString()}"
//                        )
//                    }
//                } else {
//                    tvData.text = response.code().toString()
//                }
//            })
//        }

//        val options: HashMap<String, String> = HashMap()
//        options["_sort"] = "id"
//        options["_order"] = "asc"
//        btnDone.setOnClickListener {
//            viewModel.run {
//                getCustomPostMap(edtNumber.text.toString().toInt(), options)
//                myResponseMap.observe(this@MainActivity, Observer { response ->
//                    if (response.isSuccessful) {
//                        tvData.text = response.body().toString()
//                        response.body()?.forEach {
//                            Log.i(
//                                "thuanoc",
//                                "${it.userId.toString()} \n ${it.id.toString()} \n ${it.title.toString()}"
//                            )
//                        }
//                    } else {
//                        tvData.text = response.code().toString()
//                    }
//                })
//            }
//
//
////            viewModel.run {
////                getCustomPostMap(edtNumber.text.toString().toInt(), options)
////                myResponseMap.observe(this@MainActivity, Observer { response ->
////                    if (response.isSuccessful) {
////                        tvData.text = response.body().toString()
////                        response.body()?.forEach {
////                            Log.i(
////                                "thuanoc",
////                                "${it.userId.toString()} \n ${it.id.toString()} \n ${it.title.toString()}"
////                            )
////                        }
////                    } else {
////                        tvData.text = response.code().toString()
////                    }
////                })
////            }
//
//
//        }


//        val myPost = Post(2, 2, "ThuanOc", "DemoRetrofit")
//        viewModel.pushPost(myPost)
//        //viewModel.pushPostEncoded(3, 3, "ThuanOc2", "DemoRetrofit2")
//        viewModel.myResponse.observe(this@MainActivity, Observer { response ->
//            if (response.isSuccessful) {
//                Log.i("thuanoc", "PUSH POST:  ${response.body().toString()}")
//            } else {
//                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
//            }
//        })








        btnDone.setOnClickListener {
            viewModel.run {

                RetrofitInstance.api.searchAllPhotos(edtNumber.text.toString())
                    .enqueue(object : Callback<List<Photo>> {


                        override fun onResponse(
                            call: Call<List<Photo>>,
                            response: Response<List<Photo>>
                        ) {


                            Log.e("thuanoc", "log: -----------------------------")
                            Log.d("thuanoc", "onResponse: " + response.body())

                            if (response.raw().networkResponse != null) {
                                Log.d(
                                    "thuanoc",
                                    "onResponse: response is from NETWORK...${response.body()?.size}"
                                )
                            } else if (response.raw().cacheResponse != null &&
                                response.raw().networkResponse == null
                            ) {
                                Log.d(
                                    "thuanoc",
                                    "onResponse: response is from CACHE... ${response.body()?.size}"
                                )

                            }

                        }

                        override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Failure", Toast.LENGTH_SHORT).show()
                        }

                    })

//                searchAllPhotos(edtNumber.text.toString())
//                myResponseSearchALL.observe(this@MainActivity, Observer { response ->
//                    response.enqueue(object : Callback<List<Photo>> {
//
//                        override fun onResponse(
//                            call: Call<List<Photo>>,
//                            response: Response<List<Photo>>
//                        ) {
//
//
//                            Log.e("thuanoc", "log: -----------------------------")
//                            Log.d("thuanoc", "onResponse: " + response.body())
//
//                            if (response.raw().networkResponse != null) {
//                                Log.d(
//                                    "thuanoc",
//                                    "onResponse: response is from NETWORK...${response.body()?.size}"
//                                )
//                            } else if (response.raw().cacheResponse != null &&
//                                response.raw().networkResponse == null
//                            ) {
//                                Log.d(
//                                    "thuanoc",
//                                    "onResponse: response is from CACHE... ${response.body()?.size}"
//                                )
//
//                            }
//
//                        }
//
//                        override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
//                            Toast.makeText(this@MainActivity, "Failure", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
//                })


            }

        }







    }


}