package com.example.movieapp.repository

import com.example.movieapp.Network.ApiHelper
import com.example.movieapp.Network.Movies.SimilarMoviesResponse
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
){
    suspend fun getNowShowingMovies(   apiKey: String,
                                       language: String,
                                       page: Int) = apiHelper.getNowShowingMovies(apiKey,language,page)

    suspend fun getPopularMovies(   apiKey: String,
                                       language: String,
                                       page: Int) = apiHelper.getPopularMovies(apiKey,language,page)
    suspend fun getUpcomingMovies(   apiKey: String,
                                       language: String,
                                       page: Int) = apiHelper.getUpcomingMovies(apiKey,language,page)
    suspend fun getTopRatedMovies(   apiKey: String,
                                       language: String,
                                       page: Int) = apiHelper.getTopRatedMovies(apiKey,language,page)

    suspend fun getMovieDetails(      id:Int,
                                      apiKey: String) = apiHelper.getMovieDetails(id,apiKey)

    suspend fun getSimilarMovies(      movieId: Int,
                                       apiKey: String,
                                       page: Int) = apiHelper.getSimilarMovies(movieId,apiKey,page)

    suspend fun getMovieVideos(       movieId: Int,
                                       apiKey: String) = apiHelper.getMovieVideos(movieId,apiKey)

    suspend fun getMovieCredits(       movieId: Int,
                                      apiKey: String) = apiHelper.getMovieCredits(movieId,apiKey)

    suspend fun getSearchResponse(      apiKey: String,
                                        query: String,
                                        page:Int) = apiHelper.getSearchResponse(apiKey,query,page)


}