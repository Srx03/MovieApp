package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    val popularMovieList: MutableLiveData<Movie> = MutableLiveData()
     val recentMovieList: MutableLiveData<Movie> = MutableLiveData()
     val topRatedMovieList: MutableLiveData<Movie> = MutableLiveData()
     val popularTvList: MutableLiveData<Tv> = MutableLiveData()
     val topRatedTvList: MutableLiveData<Tv> = MutableLiveData()

    init {
        getPopularMovies()
        getRecentMovies()
        getTopRatedMovies()
        getPopularTv()
        getTopRatedTv()
    }

    fun getPopularMovies() = viewModelScope.launch {
        retrofitRepostory.getPopularMovies().let { response ->

            if (response.isSuccessful){
               popularMovieList.postValue(response.body())
            }else{
                Log.d("Popular", "getPopularMovies Error: ${response.code()}")
            }
        }
    }


    fun getRecentMovies() = viewModelScope.launch {
        retrofitRepostory.getRecentMovies().let { response ->

            if (response.isSuccessful){
              recentMovieList.postValue(response.body())
            }else{
                Log.d("Recent", "getRecentMovies Error: ${response.code()}")
            }
        }
    }

    fun getTopRatedMovies() = viewModelScope.launch {
        retrofitRepostory.getTopRatedMovies().let { response ->

            if (response.isSuccessful){
              topRatedMovieList.postValue(response.body())
            }else{
                Log.d("TopRated", "getTopRatedMovies Error: ${response.code()}")
            }
        }
    }

    fun getPopularTv() = viewModelScope.launch {
        retrofitRepostory.getPopularTv().let { response ->

            if (response.isSuccessful){
             popularTvList.postValue(response.body())
            }else{
                Log.d("PopularTv", "getPopularTv Error: ${response.code()}")
            }
        }
    }

    fun getTopRatedTv() = viewModelScope.launch {
        retrofitRepostory.getTopRatedTv().let { response ->

            if (response.isSuccessful){
                topRatedTvList.postValue(response.body())
            }else{
                Log.d("TopRated", "getTopRatedTv Error: ${response.code()}")
            }
        }
    }




}