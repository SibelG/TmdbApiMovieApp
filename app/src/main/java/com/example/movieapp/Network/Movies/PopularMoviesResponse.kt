package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName




class PopularMoviesResponse (
    @SerializedName("page")
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    var totalPages: Int,

    @SerializedName("results")
    var  results: List<MovieBrief>
)