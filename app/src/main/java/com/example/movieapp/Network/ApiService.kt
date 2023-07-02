package com.example.movieapp.Network


import com.example.movieapp.Network.Movies.*
import com.example.movieapp.Network.Movies.GenresList
import com.example.movieapp.Network.tvshows.*
import com.example.movieapp.Network.videos.VideosResponse
import com.example.movieapp.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowShowingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,

    ): Response<NowShowingMoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,

    ): Response<PopularMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,

    ): Response<UpcomingMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,

    ): Response<TopRatedMoviesResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int, @Query("api_key") apiKey: String): Response<Movies>

    @GET("search/multi")
    suspend fun getSearchResponse(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<SearchResponse>
   @GET("movie/{id}/videos")
    suspend fun getMovieVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<VideosResponse>

    @GET("movie/{id}/credits")
    suspend fun getMovieCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieCreditsResponse>

    @GET("movie/{id}/similar")
    suspend fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<SimilarMoviesResponse>

    @GET("genre/movie/list")
    suspend fun getMovieGenresList(@Query("api_key") apiKey: String): Response<GenresList>

    //TV SHOWS

    //TV SHOWS
    @GET("tv/airing_today")
    suspend fun getAiringTodayTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<AiringTodayTVShowsResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<OnTheAirTVShowsResponse>

    @GET("tv/popular")
    suspend fun getPopularTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PopularTVShowsResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTVShows(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TopRatedTVShowsResponse>

    @GET("tv/{id}")
    suspend fun getTVShowDetails(
        @Path("id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShow>

    @GET("tv/{id}/videos")
    suspend fun getTVShowVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<VideosResponse>

    @GET("tv/{id}/credits?")
    suspend fun getTVShowCredits(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVShowCreditsResponse>

    @GET("tv/{id}/similar")
    suspend fun getSimilarTVShows(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<SimilarTVShowsResponse>

    @GET("genre/tv/list")
    suspend fun getTVShowGenresList(@Query("api_key") apiKey: String): Response<com.example.movieapp.Network.tvshows.GenresList>

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

    @GET("person/{id}/tv_credits")
    suspend fun getTVCastsOfPerson(
        @Path("id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<TVCastsOfPersonResponse>
}

