package com.example.movieapp.data.remote


import com.example.movieapp.models.Movie

import retrofit2.Response
import javax.inject.Inject


class RetrofitRepostory @Inject constructor(
   private val apiService: ApiService
){
     suspend fun getPopularMovies() = apiService.getPopularMovies()
     suspend fun getRecentMovies() = apiService.getRecentMovies()
     suspend fun getUpcomingMovies() = apiService.getUpcomingMovies()
     suspend fun getTopRatedMovies() = apiService.getTopRatedMovies()
     suspend fun getMovieCredits(movieId: String)  = apiService.getMovieCredits(movieId)
     suspend fun getSimilarMovies(movieId: String)  = apiService.getSimilarMovies(movieId)
     suspend fun getSearchMovieData(query: String) = apiService.getSearchMovieData(query)
     suspend fun getPopularTv() = apiService.getPopularTv()
     suspend fun getRecentTv() = apiService.getRecentTv()
     suspend fun getUpcomingTv() = apiService.getUpcomingTv()
     suspend fun getTopRatedTv() = apiService.getTopRatedTv()
     suspend fun getTvCredits(tvId:  String) = apiService.getTvCredits(tvId)
     suspend fun getSearchTvData(query:  String) = apiService.getSearchTvData(query)
     suspend fun getTrendingMovies() = apiService.getTrendigMovies()
     suspend fun getTrendingTv() = apiService.getTrendigTv()
     suspend fun getTrendingActor() = apiService.getTrendigActor()


}
