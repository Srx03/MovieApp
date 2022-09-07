package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.movie.MovieCredits
import com.example.movieapp.models.movie.MovieDetail
import com.example.movieapp.models.tv.TvDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowViewModel@Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    val movieCreditsList: MutableLiveData<MovieCredits> = MutableLiveData()
    val similarMovieList: MutableLiveData<Movie> = MutableLiveData()
    val movieDetailList: MutableLiveData<MovieDetail> = MutableLiveData()
    val tvDetailList: MutableLiveData<TvDetail> = MutableLiveData()




    fun getMovieCredits(movieId: String) = viewModelScope.launch {
        retrofitRepostory.getMovieCredits(movieId).let { response ->

            if (response.isSuccessful){
                movieCreditsList.postValue(response.body())
            }else{
                Log.d("MovieCredits", "getMovieCredits Error: ${response.code()}")
            }
        }
    }

    fun getSimilarMovie(movieId: String) = viewModelScope.launch {
        retrofitRepostory.getSimilarMovies(movieId).let { response ->

            if (response.isSuccessful){
                similarMovieList.postValue(response.body())
            }else{
                Log.d("SimilarMovie", "getSimilarMovie Error: ${response.code()}")
            }
        }
    }

    fun getMoiveDetail(movieId: String) = viewModelScope.launch {
        retrofitRepostory.getMovieDetail(movieId).let { response ->

            if (response.isSuccessful){
                movieDetailList.postValue(response.body())
                Log.d("MovieDetail", "Launched")
            }else{
                Log.d("MovieDetail", "getMovieDetail Error: ${response.code()}")
            }
        }
    }

    fun getTvDetail(tvId: String) = viewModelScope.launch {
        retrofitRepostory.getTvDetail(tvId).let { response ->

            if (response.isSuccessful){
                tvDetailList.postValue(response.body())
                Log.d("TvDetail", "Launched")
            }else{
                Log.d("MovieDetail", "getMovieDetail Error: ${response.code()}")
            }
        }
    }

}