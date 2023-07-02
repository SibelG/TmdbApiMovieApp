package com.example.movieapp.Network.videos

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("results")
    val  videos:List<Video>
)
