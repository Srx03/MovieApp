package com.example.movieapp.di.retrofit

import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Trailer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {


    @GET("3/movie/popular?api_key=0d78a49b1a3056a1df36e1de7787fcda")
     fun getPopularVideos(@Query("page") query: String) : Call<Movie>

    @GET("3/movie/now_playing?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    fun getRecentVideos(@Query("page") query: String) : Call<Movie>

    @GET("3/genre/movie/list?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    fun getGeners() : Call<Genre>

    @GET("3/movie/{id}/videos?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    fun  getTrailerTeasers(@Path("id") id: Int) : Call<Trailer>


    @GET("3/search/movie?api_key=0d78a49b1a3056a1df36e1de7787fcda&language=en-US")
    fun getSuggestions(@Query("page") query: String) : Call<Movie>


}