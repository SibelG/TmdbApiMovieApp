package com.example.movieapp.Network

import com.example.movieapp.Network.Movies.*
import com.example.movieapp.Network.tvshows.*
import com.example.movieapp.Network.tvshows.GenresList
import com.example.movieapp.Network.videos.VideosResponse
import com.example.movieapp.search.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{
    override suspend fun getNowShowingMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Response<NowShowingMoviesResponse> = apiService.getNowShowingMovies(apiKey,language,page)

    override suspend fun getPopularMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Response<PopularMoviesResponse> = apiService.getPopularMovies(apiKey,language,page)


    override suspend fun getUpcomingMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Response<UpcomingMoviesResponse> = apiService.getUpcomingMovies(apiKey,language,page)

    override suspend fun getTopRatedMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Response<TopRatedMoviesResponse> = apiService.getTopRatedMovies(apiKey,language,page)

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): Response<Movies> =apiService.getMovieDetails(movieId, apiKey)

    override suspend fun getSearchResponse(
        apiKey: String,
        query: String,
        page: Int
    ): Response<SearchResponse> = apiService.getSearchResponse(apiKey,query,page)

    override suspend fun getMovieVideos(movieId: Int, apiKey: String): Response<VideosResponse> = apiService.getMovieVideos(movieId,apiKey)

    override suspend fun getMovieCredits(
        movieId: Int,
        apiKey: String
    ): Response<MovieCreditsResponse> = apiService.getMovieCredits(movieId,apiKey)

    override suspend fun getSimilarMovies(
        movieId: Int,
        apiKey: String,
        page: Int
    ): Response<SimilarMoviesResponse> = apiService.getSimilarMovies(movieId,apiKey,page)

    override suspend fun getMovieGenresList(apiKey: String): Response<com.example.movieapp.Network.Movies.GenresList> = apiService.getMovieGenresList(apiKey)


    override suspend fun getAiringTodayTVShows(
        apiKey: String,
        page: Int
    ): Response<AiringTodayTVShowsResponse> = apiService.getAiringTodayTVShows(apiKey,page)

    override suspend fun getOnTheAirTVShows(
        apiKey: String,
        page: Int
    ): Response<OnTheAirTVShowsResponse> =apiService.getOnTheAirTVShows(apiKey,page)

    override suspend fun getPopularTVShows(
        apiKey: String,
        page: Int
    ): Response<PopularTVShowsResponse> = apiService.getPopularTVShows(apiKey,page)

    override suspend fun getTopRatedTVShows(
        apiKey: String,
        page: Int
    ): Response<TopRatedTVShowsResponse> = apiService.getTopRatedTVShows(apiKey,page)

    override suspend fun getTVShowDetails(tvShowId: Int, apiKey: String): Response<TVShow> = getTVShowDetails(tvShowId,apiKey)

    override suspend fun getTVShowVideos(movieId: Int, apiKey: String): Response<VideosResponse> = apiService.getTVShowVideos(movieId,apiKey)

    override suspend fun getTVShowCredits(
        movieId: Int,
        apiKey: String
    ): Response<TVShowCreditsResponse> = apiService.getTVShowCredits(movieId,apiKey)

    override suspend fun getSimilarTVShows(
        movieId: Int,
        apiKey: String,
        page: Int
    ): Response<SimilarTVShowsResponse> = apiService.getSimilarTVShows(movieId,apiKey,page)

    override suspend fun getTVShowGenresList(apiKey: String): Response<GenresList> = apiService.getTVShowGenresList(apiKey)

    override suspend fun getTVCastsOfPerson(
        personId: Int,
        apiKey: String
    ): Response<TVCastsOfPersonResponse> = apiService.getTVCastsOfPerson(personId,apiKey)

}