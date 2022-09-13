package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.genres.Genre
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComingSoonViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {


    private val _comingSoonMovieList = MutableLiveData<Resource<Movie>>()
    val comingSoonMovieList: LiveData<Resource<Movie>> = _comingSoonMovieList

    private val _comingSoonTvList = MutableLiveData<Resource<Tv>>()
    val comingSoonTvList: LiveData<Resource<Tv>> = _comingSoonTvList



    fun getComingSoonMovies() = viewModelScope.launch {
        _comingSoonMovieList.postValue(Resource.Loading())
        _comingSoonMovieList.postValue(retrofitRepostory.getUpcomingMovies())
    }

    fun getComingSoonTv() = viewModelScope.launch {
        _comingSoonTvList.postValue(Resource.Loading())
        _comingSoonTvList.postValue(retrofitRepostory.getUpcomingTv())
    }



}