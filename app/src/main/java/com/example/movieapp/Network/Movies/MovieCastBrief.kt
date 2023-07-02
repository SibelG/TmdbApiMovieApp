package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName




data class MovieCastBrief (

    @SerializedName("cast_id")
    val castId: Int,

    @SerializedName("character")
    val character: String,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("order")
    val order: Int,

    @SerializedName("profile_path")
    val profilePath: String,

)