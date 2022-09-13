package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.entity.ActorDetail
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.actor.ActorCredits
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.movie.MovieCredits
import com.example.movieapp.models.movie.MovieDetail
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.models.tv.TvCredits
import com.example.movieapp.models.tv.TvDetail
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel@Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    private val _movieCreditsList = MutableLiveData<Resource<MovieCredits>>()
    val movieCreditsList: LiveData<Resource<MovieCredits>> = _movieCreditsList

    private val _similarMovieList = MutableLiveData<Resource<Movie>>()
    val similarMovieList: LiveData<Resource<Movie>> = _similarMovieList

    private val _movieDetailList = MutableLiveData<Resource<MovieDetail>>()
    val movieDetailList: LiveData<Resource<MovieDetail>> = _movieDetailList


    private val _tvCreditsList = MutableLiveData<Resource<TvCredits>>()
    val tvCreditsList: LiveData<Resource<TvCredits>> = _tvCreditsList

    private val _similarTvList = MutableLiveData<Resource<Tv>>()
    val similarTvList: LiveData<Resource<Tv>> = _similarTvList

    private val _tvDetailList = MutableLiveData<Resource<TvDetail>>()
    val tvDetailList: LiveData<Resource<TvDetail>> = _tvDetailList




    fun getMovieCredits(movieId: String) = viewModelScope.launch {
       _movieCreditsList.postValue(Resource.Loading())
       _movieCreditsList.postValue(retrofitRepostory.getMovieCredits(movieId))
    }

    fun getSimilarMovie(movieId: String) = viewModelScope.launch {
        _similarMovieList.postValue(Resource.Loading())
        _similarMovieList.postValue(retrofitRepostory.getSimilarMovies(movieId))
    }

    fun getMoiveDetail(movieId: String) = viewModelScope.launch {
        _movieDetailList.postValue(Resource.Loading())
        _movieDetailList.postValue(retrofitRepostory.getMovieDetail(movieId))
    }

    fun getTvDetail(tvId: String) = viewModelScope.launch {
        _tvDetailList.postValue(Resource.Loading())
        _tvDetailList.postValue(retrofitRepostory.getTvDetail(tvId))
    }

    fun getTvCredits(tvId: String) = viewModelScope.launch {
        _tvCreditsList.postValue(Resource.Loading())
        _tvCreditsList.postValue(retrofitRepostory.getTvCredits(tvId))
    }

    fun getSimilarTv(tvId: String) = viewModelScope.launch {
        _similarTvList.postValue(Resource.Loading())
        _similarTvList.postValue(retrofitRepostory.getSimialrTv(tvId))
    }


}