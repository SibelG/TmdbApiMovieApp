package com.example.movieapp.Network.videos

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id:String,
//iso_639_1 missing
//iso_3166_1 missing
    @SerializedName("key")
    val  key:String,
    @SerializedName("name")
    val  name:String,
    @SerializedName("site")
    val  site:String,
    @SerializedName("size")
    val  size:Int,
    @SerializedName("type")
    val  type:String
)
