package com.example.movieapp.data.remote


import com.example.movieapp.util.ErrorType
import com.example.movieapp.util.Genres
import com.example.movieapp.util.Resource
import com.squareup.okhttp.ResponseBody
import javax.inject.Inject

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class RetrofitRepostory @Inject constructor(
   private val apiService: ApiService
){
     suspend fun getPopularMovies() = safeApiCall { apiService.getPopularMovies() }
     suspend fun getRecentMovies() = safeApiCall { apiService.getRecentMovies() }
     suspend fun getUpcomingMovies() = safeApiCall { apiService.getUpcomingMovies() }
     suspend fun getTopRatedMovies() = safeApiCall { apiService.getTopRatedMovies() }
     suspend fun getMovieCredits(movieId: String)  = safeApiCall { apiService.getMovieCredits(movieId) }
     suspend fun getMovieDetail(movieId: String)  = safeApiCall { apiService.getMovieDetail(movieId) }
     suspend fun getSimilarMovies(movieId: String)  = safeApiCall { apiService.getSimilarMovies(movieId) }
     suspend fun getSearchMovieData(query: String) = safeApiCall { apiService.getSearchMovieData(query) }
     suspend fun getPopularTv() = safeApiCall { apiService.getPopularTv() }
     suspend fun getRecentTv() = apiService.getRecentTv()
     suspend fun getUpcomingTv() = safeApiCall { apiService.getUpcomingTv() }
     suspend fun getTopRatedTv() = safeApiCall { apiService.getTopRatedTv() }
     suspend fun getTvCredits(tvId:  String) = safeApiCall { apiService.getTvCredits(tvId) }
     suspend fun getSimialrTv(tvId: String) = safeApiCall { apiService.getSimilarTv(tvId) }
     suspend fun getTvDetail(tvId: String) = safeApiCall { apiService.getTvDetail(tvId) }
     suspend fun getSearchTvData(query:  String) = safeApiCall { apiService.getSearchTvData(query) }
     suspend fun getTrendingMovies() = safeApiCall { apiService.getTrendigMovies() }
     suspend fun getTrendingTv() = safeApiCall { apiService.getTrendigTv() }
     suspend fun getTrendingActor() = safeApiCall { apiService.getTrendigActor() }
     suspend fun getSearchActorData(query: String) = safeApiCall { apiService.getSearchActorData(query) }
     suspend fun getActorDetail(personId: String) = safeApiCall { apiService.getActorDetail(personId) }
     suspend fun getActorCredits(personId: String) = safeApiCall { apiService.getActorCredits(personId) }
     suspend fun getMoviesByGenres(genresId: String) = safeApiCall { apiService.getMoviesByGenres(genresId) }
     suspend fun getTvByGenres(genresId: String) = safeApiCall { apiService.getTvByGenres(genresId) }



     suspend fun <T> safeApiCall(api: suspend () -> Response<T>): Resource<T> {

          return withContext(Dispatchers.IO) {
               try {
                    val response: Response<T> = api()
                    if (response.isSuccessful) {
                         Resource.Success(data = response.body()!!)
                    } else {
                        Resource.Error("Something went wrong",
                        errorType = ErrorType.HTTP)
                    }
               } catch (throwable: Throwable) {
                    when (throwable) {
                         is IOException -> Resource.Error(
                              "Please check your network connection",
                              errorType = ErrorType.NETWORK
                         )

                         is HttpException ->
                              Resource.Error(
                                   errorMessage = throwable.message ?: "Something went wrong",
                                   errorType = ErrorType.HTTP
                              )

                         else -> Resource.Error(
                              errorMessage = "Something went wrong",
                              errorType = ErrorType.UNKNOWN
                         )
                    }
               }
          }
     }




}
