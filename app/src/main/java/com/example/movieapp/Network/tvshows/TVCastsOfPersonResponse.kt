package com.example.movieapp.Network.tvshows

import com.google.gson.annotations.SerializedName

class TVCastsOfPersonResponse{
    @SerializedName("cast")
    private val casts: List<TVCastOfPerson>? = null

    //crew missing
    @SerializedName("id")
    private val id: Int? = null
}