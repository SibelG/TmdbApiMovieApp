package com.example.movieapp.view_model

import android.app.appsearch.SearchResult
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.Network.Movies.*
import com.example.movieapp.Network.videos.Video
import com.example.movieapp.Network.videos.VideosResponse
import com.example.movieapp.R
import com.example.movieapp.repository.MainRepository
import com.example.movieapp.search.SearchResponse
import com.example.movieapp.utils.NetworkHelper
import com.example.movieapp.utils.Resource
import kotlinx.coroutines.launch


class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository
): ViewModel(){

    private val _res = MutableLiveData<Resource<NowShowingMoviesResponse>>()
    val res : LiveData<Resource<NowShowingMoviesResponse>>
        get() = _res

    private val _resPopular = MutableLiveData<Resource<PopularMoviesResponse>>()
    val resPopular : LiveData<Resource<PopularMoviesResponse>>
        get() = _resPopular

    private val _resUpcoming = MutableLiveData<Resource<UpcomingMoviesResponse>>()
    val resUpcoming : LiveData<Resource<UpcomingMoviesResponse>>
        get() = _resUpcoming

    private val _resTopRated = MutableLiveData<Resource<TopRatedMoviesResponse>>()
    val resTopRated : LiveData<Resource<TopRatedMoviesResponse>>
        get() = _resTopRated

    private val _resMovieDetails = MutableLiveData<Resource<Movies>>()
    val resMovieDetails : LiveData<Resource<Movies>>
        get() = _resMovieDetails

    private val _resSimilarMovies = MutableLiveData<Resource<SimilarMoviesResponse>>()
    val resSimilarMovies : LiveData<Resource<SimilarMoviesResponse>>
        get() = _resSimilarMovies

    private val _resMovieVideos = MutableLiveData<Resource<VideosResponse>>()
    val resMovieVideos : LiveData<Resource<VideosResponse>>
        get() = _resMovieVideos

    private val _resMovieCredidts = MutableLiveData<Resource<MovieCreditsResponse>>()
    val resMovieCredidts : LiveData<Resource<MovieCreditsResponse>>
        get() = _resMovieCredidts

    private val _resSearchMovies = MutableLiveData<Resource<SearchResponse>>()
    val resSearchMovies : LiveData<Resource<SearchResponse>>
        get() = _resSearchMovies

    init {

        getNowShowing()
        getPopularMovies()
        getUpcomingMovies()
        getTopRatedMovies()
    }

    private fun getNowShowing()  = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepository.getNowShowingMovies("69d66957eebff9666ea46bd464773cf0", "en-US", 1).let {
            if (it.isSuccessful){
                _res.postValue(Resource.success(it.body()))
            }else{
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    private fun getPopularMovies()  = viewModelScope.launch {
        _resPopular.postValue(Resource.loading(null))
        mainRepository.getPopularMovies("69d66957eebff9666ea46bd464773cf0", "en-US", 1).let {
            if (it.isSuccessful){
                _resPopular.postValue(Resource.success(it.body()))
            }else{
                _resPopular.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    private fun getUpcomingMovies()  = viewModelScope.launch {
        _resUpcoming.postValue(Resource.loading(null))
        mainRepository.getUpcomingMovies("69d66957eebff9666ea46bd464773cf0", "en-US", 1).let {
            if (it.isSuccessful){
                _resUpcoming.postValue(Resource.success(it.body()))
            }else{
                _resUpcoming.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    private fun getTopRatedMovies()  = viewModelScope.launch {
        _resTopRated.postValue(Resource.loading(null))
        mainRepository.getTopRatedMovies("69d66957eebff9666ea46bd464773cf0", "en-US", 1).let {
            if (it.isSuccessful){
                _resTopRated.postValue(Resource.success(it.body()))
            }else{
                _resTopRated.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
     fun  getMovieDetails(id:Int) = viewModelScope.launch {
        _resMovieDetails.postValue(Resource.loading(null))
        mainRepository.getMovieDetails(id, "69d66957eebff9666ea46bd464773cf0").let {
            if (it.isSuccessful){
                _resMovieDetails.postValue(Resource.success(it.body()))
            }else{
                _resMovieDetails.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
    fun getSimilarMovies(id:Int) = viewModelScope.launch {
        _resSimilarMovies.postValue(Resource.loading(null))
        mainRepository.getSimilarMovies(id, "69d66957eebff9666ea46bd464773cf0",1).let {
            if (it.isSuccessful){
                _resSimilarMovies.postValue(Resource.success(it.body()))
            }else{
                _resSimilarMovies.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
     fun getMovieVideos(id:Int) = viewModelScope.launch {
        _resMovieVideos.postValue(Resource.loading(null))
        mainRepository.getMovieVideos(id, "69d66957eebff9666ea46bd464773cf0").let {
            if (it.isSuccessful){

                _resMovieVideos.postValue(Resource.success(it.body()))
            }else{
                _resMovieVideos.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
    fun getMovieCredits(id:Int) = viewModelScope.launch {
        _resMovieCredidts.postValue(Resource.loading(null))
        mainRepository.getMovieCredits(id, "69d66957eebff9666ea46bd464773cf0").let {
            if (it.isSuccessful){
                _resMovieCredidts.postValue(Resource.success(it.body()))
            }else{
                _resMovieCredidts.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }
    fun getSearchMovies(query:String,page:Int) = viewModelScope.launch {
        _resSearchMovies.postValue(Resource.loading(null))
        mainRepository.getSearchResponse("69d66957eebff9666ea46bd464773cf0",query,page).let {
            if (it.isSuccessful){
                _resSearchMovies.postValue(Resource.success(it.body()))
            }else{
                _resSearchMovies.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }


}