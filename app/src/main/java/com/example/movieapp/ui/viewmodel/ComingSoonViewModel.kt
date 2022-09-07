package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.genres.Genre
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComingSoonViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {


    val comingSoonMovieList: MutableLiveData<Movie> = MutableLiveData()
    val comingSoonTvList: MutableLiveData<Tv> = MutableLiveData()



    fun getComingSoonMovies() = viewModelScope.launch {
        retrofitRepostory.getUpcomingMovies().let { response ->

            if (response.isSuccessful){
                comingSoonMovieList.postValue(response.body())
            }else{
                Log.d("comingSoon", "getComingSoonMovie Error: ${response.code()}")
            }
        }
    }

    fun getComingSoonTv() = viewModelScope.launch {
        retrofitRepostory.getUpcomingTv().let { response ->

            if (response.isSuccessful){
                comingSoonTvList.postValue(response.body())
            }else{
                Log.d("comingSoon", "getComingSoonTv Error: ${response.code()}")
            }
        }
    }



}