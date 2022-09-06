package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.genres.Genre
import com.example.movieapp.models.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComingSoonViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {


    val comingSoonMovieList: MutableLiveData<Movie> = MutableLiveData()
    val comingSoonGenreList: MutableLiveData<Genre> = MutableLiveData()

init {
    getComingSoonMovies()

}

    fun getComingSoonMovies() = viewModelScope.launch {
        retrofitRepostory.getUpcomingMovies().let { response ->

            if (response.isSuccessful){
                comingSoonMovieList.postValue(response.body())
            }else{
                Log.d("comingSoon", "getComingSoonMovie Error: ${response.code()}")
            }
        }
    }



}