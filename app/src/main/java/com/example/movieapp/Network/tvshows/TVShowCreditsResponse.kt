package com.example.movieapp.Network.tvshows

import com.example.movieapp.Network.tvshows.TVShowCastBrief
import com.google.gson.annotations.SerializedName
import com.example.movieapp.Network.tvshows.TVShowCrewBrief

class TVShowCreditsResponse(
    @SerializedName("cast")
    var casts: List<TVShowCastBrief>,
    @SerializedName("crew")
    var crews: List<TVShowCrewBrief>,
    @SerializedName("id")
    var id: Int
)