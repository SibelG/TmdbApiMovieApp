package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName

data class MovieBrief (
    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("id")
    var id: Int,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("title")
    var title: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    var posterPath: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("release_date")
    val releaseDate: String,
) {
    constructor() : this(0,0,false,0.0,"",0.0,"","","", emptyList(),"",false,"","")
}