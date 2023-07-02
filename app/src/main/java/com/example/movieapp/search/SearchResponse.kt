package com.example.movieapp.search

data class SearchResponse (
    val page: Int,
    var results: List<SearchResult>,
    val totalPages: Int

)