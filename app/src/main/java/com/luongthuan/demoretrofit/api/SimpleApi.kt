package com.luongthuan.demoretrofit.api

import com.luongthuan.demoretrofit.models.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Luong Thuan on 14/08/2022.
 */
interface SimpleApi {

    @GET("posts/1")
    suspend fun getPosts(): Response<Post>

    @GET("posts/{postNumber}")
    suspend fun getPostNumber(@Path("postNumber") number: Int): Response<Post>

    @GET("posts")
    suspend fun getCustomPost(
        @Query("userId") userId: Int,
        @Query("_sort") sort: String,
        @Query("_order") order: String
    ): Response<List<Post>>

    @GET("posts")
    suspend fun getCustomPostMap(
        @Query("userId") userId: Int,
        @QueryMap options: Map<String, String>
    ): Response<List<Post>>
}