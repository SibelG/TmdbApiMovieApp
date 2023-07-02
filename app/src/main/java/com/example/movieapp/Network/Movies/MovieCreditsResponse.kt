package com.example.movieapp.Network.Movies

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id")
    var id:Int,
    @SerializedName("cast")
    var casts: List<MovieCastBrief>,
    @SerializedName("crew")
    var crews: List<MovieCrewBrief>
)
