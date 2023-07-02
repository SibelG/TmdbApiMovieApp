package com.example.movieapp.Network.tvshows

import com.google.gson.annotations.SerializedName

class TVCastOfPerson {
    @SerializedName("credit_id")
    private val creditId: String? = null

    @SerializedName("original_name")
    private val originalName: String? = null

    @SerializedName("id")
    private val id: Int? = null

    @SerializedName("genre_ids")
    private val genreIds: List<Int>? = null

    @SerializedName("character")
    private val character: String? = null

    @SerializedName("name")
    private val name: String? = null

    @SerializedName("poster_path")
    private val posterPath: String? = null

    @SerializedName("vote_count")
    private val voteCount: Int? = null

    @SerializedName("vote_average")
    private val voteAverage: Double? = null

    @SerializedName("popularity")
    private val popularity: Double? = null

    @SerializedName("episode_count")
    private val episodeCount: Int? = null

    @SerializedName("original_language")
    private val originalLanguage: String? = null

    @SerializedName("first_air_date")
    private val firstAirDate: String? = null

    @SerializedName("backdrop_path")
    private val backdropPath: String? = null

    @SerializedName("overview")
    private val overview: String? = null

    @SerializedName("origin_country")
    private val originCountries: List<String>? = null
}