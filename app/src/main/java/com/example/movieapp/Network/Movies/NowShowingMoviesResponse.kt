package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName




data class NowShowingMoviesResponse (
    @SerializedName("results")
    var results: List<MovieBrief>,

    @SerializedName("page")
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    //dates missing
    @SerializedName("total_pages")
    val totalPages: Int
)