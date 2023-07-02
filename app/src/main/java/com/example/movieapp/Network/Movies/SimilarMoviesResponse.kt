package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName




data class SimilarMoviesResponse (
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<MovieBrief>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int

)