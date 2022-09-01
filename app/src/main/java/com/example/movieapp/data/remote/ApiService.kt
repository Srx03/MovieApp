package com.example.movieapp.data.remote

import com.example.movieapp.models.Genre
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MovieCredits
import com.example.movieapp.models.Tv
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("3/movie/popular?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getPopularMovies() : Response<Movie>

    @GET("3/movie/now_playing?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getRecentMovies() : Response<Movie>

    @GET("3/movie/upcoming?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getUpcomingMovies() : Response<Movie>

    @GET("3/movie/top_rated?api_key=3D0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTopRatedMovies() : Response<Movie>

    @GET("3/movie/{movieId}/credits?api_key=3D0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getMovieCredits(@Path("movieId") movieId: String) : Response<MovieCredits>

    @GET("3/search/movie?api_key=0d78a49b1a3056a1df36e1de7787fcda&language=en-US")
    suspend fun getSearchMovieData(@Query("query") query: String) : Response<Movie>


    @GET("3/tv/popular?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getPopularTv() : Response<Tv>

    @GET("3/tv/now_playing?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getRecentTv() : Response<Tv>

    @GET("3/tv/upcoming?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getUpcomingTv() : Response<Tv>

    @GET("3/tv/top_rated?api_key=3D0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTopRatedTv() : Response<Tv>

    @GET("3/tv/{tvId}/credits?api_key=3D0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTvCredits(@Path("tvId") tvId: String) : Response<MovieCredits>

    @GET("3/search/movie?api_key=0d78a49b1a3056a1df36e1de7787fcda&language=en-US")
    suspend fun getSearchTvData(@Query("query") query: String) : Response<Tv>




    @GET("3/genre/movie/list?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    fun getGeners() : Response<Genre>


}