package com.example.movieapp.Network

import com.example.movieapp.Network.Movies.*
import com.example.movieapp.Network.tvshows.*
import com.example.movieapp.Network.tvshows.GenresList
import com.example.movieapp.Network.videos.VideosResponse
import com.example.movieapp.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiHelper {

    suspend fun getNowShowingMovies(
        apiKey: String,
        language: String,
        page: Int,

        ): Response<NowShowingMoviesResponse>


    suspend fun getPopularMovies(
        apiKey: String,
        language: String,
        page: Int,

        ): Response<PopularMoviesResponse>


    suspend fun getUpcomingMovies(
        apiKey: String,
        language: String,
        page: Int,

        ): Response<UpcomingMoviesResponse>


    suspend fun getTopRatedMovies(
         apiKey: String,
         language: String,
         page: Int,

        ): Response<TopRatedMoviesResponse>


    suspend fun getMovieDetails(movieId: Int, apiKey: String): Response<Movies>

    suspend fun getSearchResponse(
        apiKey: String,
        query: String,
        page: Int,
    ): Response<SearchResponse>

    suspend fun getMovieVideos(
        movieId: Int,
        apiKey: String
    ): Response<VideosResponse>


    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieCreditsResponse>


    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<SimilarMoviesResponse>


    suspend fun getMovieGenresList(@Query("api_key") apiKey: String): Response<com.example.movieapp.Network.Movies.GenresList>

    //TV SHOWS

    //TV SHOWS

    suspend fun getAiringTodayTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<AiringTodayTVShowsResponse>


    suspend fun getOnTheAirTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<OnTheAirTVShowsResponse>


    suspend fun getPopularTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PopularTVShowsResponse>


    suspend fun getTopRatedTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TopRatedTVShowsResponse>


    suspend fun getTVShowDetails(
        @Path("id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShow>


    suspend fun getTVShowVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<VideosResponse>


    suspend fun getTVShowCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShowCreditsResponse>


    suspend fun getSimilarTVShows(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<SimilarTVShowsResponse>


    suspend fun getTVShowGenresList(@Query("api_key") apiKey: String): Response<GenresList>

    //PERSON

    //PERSON
    /*@GET("person/{id}")
    fun getPersonDetails(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<Person>

    @GET("person/{id}/movie_credits")
    fun getMovieCastsOfPerson(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieCastsOfPersonResponse>*/


    suspend fun getTVCastsOfPerson(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVCastsOfPersonResponse>
}