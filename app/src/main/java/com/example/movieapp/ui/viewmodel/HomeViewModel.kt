package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.di.retrofit.RetrofitRepostory
import com.example.movieapp.di.retrofit.RetrofitServiceInstance
import com.example.movieapp.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

   var popularMovieList: MutableLiveData<Movie>
   var recentMovieList: MutableLiveData<Movie>

    init {
        popularMovieList = MutableLiveData()
        recentMovieList = MutableLiveData()
    }

    fun getObserverLiveData(isPopular: Boolean): MutableLiveData<Movie>{
        if (isPopular){
            return popularMovieList
        }
        else
            return recentMovieList
    }


    fun loadData(page: String, isPopular: Boolean){
        if (isPopular)
            retrofitRepostory.getPopularMovies(page, popularMovieList)
        else
            retrofitRepostory.getRecentMovies(page, recentMovieList)
    }


}