package com.luongthuan.demoretrofit.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Luong Thuan on 25/08/2022.
 */
class MyInterceptor : Interceptor {

    /*Create Custom Request Headers*/
    /*HTTP Headers are mostly used to add metadata information in an API request and response*/
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Platform", "Android123")
            .addHeader("X-Auth-Token", "ThuanOc")
            .build()
        return chain.proceed(request)
    }

}