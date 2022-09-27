package com.luongthuan.demoretrofit

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import java.io.File

/**
 * Created by Luong Thuan on 25/09/2022.
 */
class MyApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MyApplication

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    fun getInstance(): MyApplication? {
        return instance
    }




}