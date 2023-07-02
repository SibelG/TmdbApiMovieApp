package com.example.movieapp.Network.tvshows

import com.google.gson.annotations.SerializedName


class TVShowBrief{
    @SerializedName("original_name")
    val originalName: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("vote_count")
    val voteCount: Int? = null

    @SerializedName("vote_average")
    val voteAverage: Double? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("first_air_date")
    val firstAirDate: String? = null

    @SerializedName("popularity")
    val popularity: Double? = null

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null

    @SerializedName("original_language")
    val originalLanguage: String? = null

    @SerializedName("backdrop_path")
    val backdropPath: String? = null

    @SerializedName("overview")
    val overview: String? = null

    @SerializedName("origin_country")
    val originCountries: List<String>? = null

}