package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName




class GenresList {
    @SerializedName("genres")
    lateinit var  genres: List<Genre>
}