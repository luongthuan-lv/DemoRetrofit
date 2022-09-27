package com.luongthuan.demoretrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luongthuan.demoretrofit.models.Photo
import com.luongthuan.demoretrofit.models.Post
import com.luongthuan.demoretrofit.respository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Luong Thuan on 15/08/2022.
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    val myResponseNumber: MutableLiveData<Response<Post>> = MutableLiveData()
    val myResponseUserId: MutableLiveData<Response<List<Post>>> = MutableLiveData()
    val myResponseMap: MutableLiveData<Response<List<Post>>> = MutableLiveData()

//    val myResponseSearchALL: MutableLiveData<Call<List<Photo>>> = MutableLiveData()
//    val myResponseSearch: MutableLiveData<Call<Photo>> = MutableLiveData()

    fun pushPost(post: Post) {
        viewModelScope.launch {
            val response = repository.pushPost(post)
            myResponse.value = response
        }
    }

    fun pushPostEncoded(userId: Int, id: Int, title: String, body: String) {
        viewModelScope.launch {
            val response = repository.pushPostEncoded(userId, id, title, body)
            myResponse.value = response
        }
    }

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getPost()
            myResponse.value = response
        }
    }


    fun getPost(auth: String) {
        viewModelScope.launch {
            val response = repository.getPost(auth)
            myResponse.value = response
        }
    }

    fun getPostNumber(number: Int) {
        viewModelScope.launch {
            val response = repository.getPostNumber(number)
            myResponseNumber.value = response
        }
    }

    fun getCustomPost(userInt: Int, sort: String, order: String) {
        viewModelScope.launch {
            myResponseUserId.value = repository.getCustomPost(userInt, sort, order)
        }
    }

    fun getCustomPostMap(userInt: Int, options: Map<String, String>) {
        viewModelScope.launch {
            myResponseMap.value = repository.getCustomPostMap(userInt, options)
        }
    }

//    fun searchAllPhotos(url: String) {
//        viewModelScope.launch {
//            myResponseSearchALL.value = repository.searchAllPhotos(url)
//        }
//    }
//
//    fun searchPhotos(id: String) {
//        viewModelScope.launch {
//            myResponseSearch.value = repository.searchPhoto(id)
//        }
//    }
}