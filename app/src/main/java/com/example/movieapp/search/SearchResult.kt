package com.example.movieapp.search

data class SearchResult(
    val id:Int,
    val poster_path:String,
    val name:String,
    val title:String,
    val media_type:String,
    val overview:String,
    val release_date:String,
    val profile_path:String

)