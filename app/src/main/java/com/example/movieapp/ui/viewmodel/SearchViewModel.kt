package com.example.movieapp.ui.viewmodel

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.entity.Actors
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.Actor

import com.example.movieapp.models.Movie
import com.example.movieapp.models.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {


    val trendingMovieList: MutableLiveData<Movie> = MutableLiveData()
    val trendingTvList: MutableLiveData<Tv> = MutableLiveData()
    val trendingActorList: MutableLiveData<Actors> = MutableLiveData()



    fun getTrendingMovies() = viewModelScope.launch {
        retrofitRepostory.getTrendingMovies().let { response ->

            if (response.isSuccessful){
               trendingMovieList.postValue(response.body())
            }else{
                Log.d("Trending", "getRecentMovie Error: ${response.code()}")
            }
        }
    }


    fun getTrendingTv() = viewModelScope.launch {
        retrofitRepostory.getTrendingTv().let { response ->

            if (response.isSuccessful){
               trendingTvList.postValue(response.body())
            }else{
                Log.d("Trending", "getRecentTv Error: ${response.code()}")
            }
        }
    }


    fun getTrendingActor() = viewModelScope.launch {
        retrofitRepostory.getTrendingActor().let { response ->

            if (response.isSuccessful){
                trendingActorList.postValue(response.body())
            }else{
                Log.d("Trending", "getRecentActor Error: ${response.code()}")
            }
        }
    }



}