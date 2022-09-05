package com.example.movieapp.ui.viewmodel

import android.util.Log

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.actor.Actors
import com.example.movieapp.data.remote.RetrofitRepostory

import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
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

    val searchedMovieList: MutableLiveData<Movie> = MutableLiveData()
    val searchedTvList: MutableLiveData<Tv> = MutableLiveData()
    val searchedActorList: MutableLiveData<Actors> = MutableLiveData()



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

    fun getSearchedMovie(searchQuery: String) = viewModelScope.launch {
        retrofitRepostory.getSearchMovieData(searchQuery).let { response ->
            if (response.isSuccessful){
                searchedMovieList.postValue(response.body())
            }else{
                Log.d("Searched", "getSearchedMovie Error: ${response.code()}")
            }
        }
    }


    fun getSearchedTv(searchQuery: String) = viewModelScope.launch {
        retrofitRepostory.getSearchTvData(searchQuery).let { response ->
            if (response.isSuccessful){
                searchedTvList.postValue(response.body())
            }else{
                Log.d("Searched", "getSearchedTv Error: ${response.code()}")
            }
        }
    }

    fun getSearchedActor(searchQuery: String) = viewModelScope.launch {
        retrofitRepostory.getSearchActorData(searchQuery).let { response ->
            if (response.isSuccessful){
                searchedActorList.postValue(response.body())
            }else{
                Log.d("Searched", "getSearchedActor Error: ${response.code()}")
            }
        }
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