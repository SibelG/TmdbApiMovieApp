package com.example.movieapp.Network.tvshows

import com.google.gson.annotations.SerializedName
import com.example.movieapp.Network.tvshows.TVShowBrief

class SimilarTVShowsResponse {
    @SerializedName("page")
    private val page: Int? = null

    @SerializedName("results")
    private val results: List<TVShowBrief>? = null

    @SerializedName("total_pages")
    private val totalPages: Int? = null

    @SerializedName("total_results")
    private val totalResults: Int? = null
}