package com.luongthuan.demoretrofit.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Luong Thuan on 26/09/2022.
 */
class Photo(
    @SerializedName("albumId")
    var albumId: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
)