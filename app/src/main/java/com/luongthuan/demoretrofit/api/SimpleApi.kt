package com.luongthuan.demoretrofit.api

import com.luongthuan.demoretrofit.models.Photo
import com.luongthuan.demoretrofit.models.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Luong Thuan on 14/08/2022.
 */
interface SimpleApi {

    /*GET request*/

    /*add header = @Headers*/
    //@Headers("Auth: ThuanOc", "Platform: AndroidPro")
    @GET("posts/1")
    suspend fun getPosts(): Response<Post>

    // add header = pram header
    @GET("posts/1")
    suspend fun getPosts(@Header("Auth") auth: String): Response<Post>

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


    /*POST request*/
    /*POST is used to send data to a sever to create/update a resource
      The data sent to the sever with POST is stored in the request body of the HTTP request*/

    /*Example data JSON:
    {
    "UserId":1,
    "Id":2,
    "Title":"ThuanOc",
    "Body":"Demo Retrofit"
    }
    */
    @POST("posts")
    suspend fun pushPost(@Body post: Post): Response<Post>

    /*Request made with this annotation will have
    application/x-www-form-urlencoded MIME type
    instead of JSON like the regular POSR request*/

    /*Example data FormUrlEncoded:

    UserId=1&Id=2&Title=ThuanOc&Body=DemoRetrofit

    */
    @FormUrlEncoded
    @POST("posts")
    suspend fun pushPostEncoded(
        @Field("userId") userId: Int,
        @Field("id") Id: Int,
        @Field("title") Title: String,
        @Field("body") body: String
    ): Response<Post>


    @GET("{url}")
    fun searchAllPhotos(
        @Path("url") url: String?
    ): Call<List<Photo>>

    @GET("photos/{id}")
    fun searchPhoto(
        @Path("id") id: String?
    ): Call<Photo>
}