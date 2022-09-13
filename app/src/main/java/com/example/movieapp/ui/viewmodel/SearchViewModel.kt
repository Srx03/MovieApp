package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.actor.Actors
import com.example.movieapp.data.remote.RetrofitRepostory

import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    private val _trendingMovieList = MutableLiveData<Resource<Movie>>()
    val trendingMovieList: LiveData<Resource<Movie>> = _trendingMovieList

    private val _trendingTvList = MutableLiveData<Resource<Tv>>()
    val trendingTvList: LiveData<Resource<Tv>> = _trendingTvList

    private val _trendingActorList = MutableLiveData<Resource<Actors>>()
    val trendingActorList: LiveData<Resource<Actors>> = _trendingActorList



    private val _searchedMovieList = MutableLiveData<Resource<Movie>>()
     val searchedMovieList: LiveData<Resource<Movie>> = _searchedMovieList

    private val _searchedTvList = MutableLiveData<Resource<Tv>>()
    val searchedTvList: LiveData<Resource<Tv>> = _searchedTvList

    private val _searchedActorList = MutableLiveData<Resource<Actors>>()
     val searchedActorList: LiveData<Resource<Actors>> = _searchedActorList



    fun getTrendingMovies() = viewModelScope.launch {
       _trendingMovieList.postValue(Resource.Loading())
        _trendingMovieList.postValue(retrofitRepostory.getTrendingMovies())
    }


    fun getTrendingTv() = viewModelScope.launch {
      _trendingTvList.postValue(Resource.Loading())
      _trendingTvList.postValue(retrofitRepostory.getTrendingTv())
    }


    fun getTrendingActor() = viewModelScope.launch {
       _trendingActorList.postValue(Resource.Loading())
       _trendingActorList.postValue(retrofitRepostory.getTrendingActor())
    }

    fun getSearchedMovie(searchQuery: String) = viewModelScope.launch {
        _searchedMovieList.postValue(Resource.Loading())
        _searchedMovieList.postValue(retrofitRepostory.getSearchMovieData(searchQuery))
    }


    fun getSearchedTv(searchQuery: String) = viewModelScope.launch {
        _searchedTvList.postValue(Resource.Loading())
        _searchedTvList.postValue(retrofitRepostory.getSearchTvData(searchQuery))
    }

    fun getSearchedActor(searchQuery: String) = viewModelScope.launch {
        _searchedActorList.postValue(Resource.Loading())
        _searchedActorList.postValue(retrofitRepostory.getSearchActorData(searchQuery))

    }

    fun getTrendingData(category: Int){
        if (category == 0)
            getTrendingMovies()
        if (category == 1)
            getTrendingTv()
        if (category == 2)
            getTrendingActor()
    }


}