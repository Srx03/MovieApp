package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    private val _popularMovieList = MutableLiveData<Resource<Movie>>()
    val popularMovieList: LiveData<Resource<Movie>> = _popularMovieList

    private val _recentMovieList = MutableLiveData<Resource<Movie>>()
     val recentMovieList: LiveData<Resource<Movie>> = _recentMovieList

    private val _topRatedMovieList = MutableLiveData<Resource<Movie>>()
     val topRatedMovieList: LiveData<Resource<Movie>> = _topRatedMovieList

    private val _popularTvList = MutableLiveData<Resource<Tv>>()
     val popularTvList: LiveData<Resource<Tv>> = _popularTvList

    private val _topRatedTvList = MutableLiveData<Resource<Tv>>()
     val topRatedTvList: LiveData<Resource<Tv>> = _topRatedTvList


    init {
       retry()
    }

    fun retry(){
        getPopularMovies()
        getRecentMovies()
        getTopRatedMovies()
        getPopularTv()
        getTopRatedTv()
    }

    fun getPopularMovies() = viewModelScope.launch {

        _popularMovieList.postValue(Resource.Loading())
        _popularMovieList.postValue(retrofitRepostory.getPopularMovies())
    }


    fun getRecentMovies() = viewModelScope.launch {
        _recentMovieList.postValue(Resource.Loading())
        _recentMovieList.postValue(retrofitRepostory.getRecentMovies())
    }

    fun getTopRatedMovies() = viewModelScope.launch {
        _topRatedMovieList.postValue(Resource.Loading())
        _topRatedMovieList.postValue(retrofitRepostory.getTopRatedMovies())
    }

    fun getPopularTv() = viewModelScope.launch {
        _popularTvList.postValue(Resource.Loading())
        _popularTvList.postValue(retrofitRepostory.getPopularTv())
    }

    fun getTopRatedTv() = viewModelScope.launch {
        _topRatedTvList.postValue(Resource.Loading())
        _topRatedTvList.postValue(retrofitRepostory.getTopRatedTv())
    }




}