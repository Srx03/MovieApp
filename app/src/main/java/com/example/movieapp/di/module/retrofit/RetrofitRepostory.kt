package com.example.movieapp.di.module.retrofit

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Trailer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject

class RetrofitRepostory @Inject constructor(
    private val retrofitServiceInstance: RetrofitServiceInstance
) {
    fun getPopularMovies(page: String, liveData: MutableLiveData<Movie>){
        retrofitServiceInstance.getPopularVideos(page).enqueue(object : Callback<Movie>{
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                liveData.postValue(null)
            }
        })
    }



}
