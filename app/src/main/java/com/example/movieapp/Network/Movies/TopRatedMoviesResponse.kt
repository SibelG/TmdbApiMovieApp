package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName




data class TopRatedMoviesResponse (
    @SerializedName("page")
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("results")
    var results: List<MovieBrief>
)