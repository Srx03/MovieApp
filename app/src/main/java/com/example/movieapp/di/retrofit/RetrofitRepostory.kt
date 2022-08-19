package com.example.movieapp.di.retrofit

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
