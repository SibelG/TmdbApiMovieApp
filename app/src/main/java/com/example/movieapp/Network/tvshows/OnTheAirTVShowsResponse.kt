package com.example.movieapp.Network.tvshows

import com.google.gson.annotations.SerializedName
import com.example.movieapp.Network.tvshows.TVShowBrief

class OnTheAirTVShowsResponse {
    @SerializedName("page")
    val page: Int? = null

    @SerializedName("results")
    lateinit var results: List<TVShowBrief>

    @SerializedName("total_results")
    val totalResults: Int? = null

    @SerializedName("total_pages")
    val totalPages: Int? = null
}