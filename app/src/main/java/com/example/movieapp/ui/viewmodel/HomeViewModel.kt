package com.example.movieapp.ui.viewmodel

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

    init {
         popularMovieList = MutableLiveData<Movie>()
    }

    fun getObserverLiveData(): MutableLiveData<Movie>{
        return popularMovieList
    }

    fun loadPopularData(page: String){
        retrofitRepostory.getPopularMovies(page, popularMovieList)
    }


}