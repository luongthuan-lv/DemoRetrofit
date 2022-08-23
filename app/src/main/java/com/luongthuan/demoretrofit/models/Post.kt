package com.luongthuan.demoretrofit.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Luong Thuan on 14/08/2022.
 */
data class Post(
    @SerializedName("userId")
    var userId: Int? = null,

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("body")
    var body: String? = null
)