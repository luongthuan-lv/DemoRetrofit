package com.luongthuan.demoretrofit.respository

import com.luongthuan.demoretrofit.api.RetrofitInstance
import com.luongthuan.demoretrofit.models.Post
import retrofit2.Response

/**
 * Created by Luong Thuan on 15/08/2022.
 */
class Repository {

    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun getPostNumber(number: Int): Response<Post> {
        return RetrofitInstance.api.getPostNumber(number)
    }

    suspend fun getCustomPost(userId: Int, sort: String, order: String): Response<List<Post>> {
        return RetrofitInstance.api.getCustomPost(userId, sort, order)
    }

    suspend fun getCustomPostMap(userId: Int, options: Map<String, String>): Response<List<Post>> {
        return RetrofitInstance.api.getCustomPostMap(userId, options)
    }
}