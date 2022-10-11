package com.example.movieapp.data.remote

import com.example.movieapp.models.actor.ActorCredits
import com.example.movieapp.models.actor.ActorDetail
import com.example.movieapp.models.actor.Actors
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.movie.MovieCredits
import com.example.movieapp.models.movie.MovieDetail
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.models.tv.TvCredits
import com.example.movieapp.models.tv.TvDetail
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

    @GET("3/movie/top_rated?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTopRatedMovies() : Response<Movie>

    @GET("3/movie/{movieId}/similar?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getSimilarMovies(@Path("movieId") movieId: String) : Response<Movie>

    @GET("3/movie/{movieId}/credits?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getMovieCredits(@Path("movieId") movieId: String) : Response<MovieCredits>

    @GET("3/movie/{movieId}?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getMovieDetail(@Path("movieId") movieId: String) : Response<MovieDetail>

    @GET("3/search/movie?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getSearchMovieData(@Query("query") query: String) : Response<Movie>

    @GET("3/trending/movie/week?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTrendigMovies() : Response<Movie>

    @GET("3/discover/movie?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getMoviesByGenres(
        @Query("with_genres") genres: String, // It will require comma separated string of genres ids
        @Query("sort_by") sortBy: String? = "popularity.desc"): Response<Movie>




    @GET("3/tv/popular?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getPopularTv() : Response<Tv>

    @GET("3/tv/now_playing?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getRecentTv() : Response<Tv>

    @GET("3/tv/top_rated?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTopRatedTv() : Response<Tv>

    @GET("3/tv/{tvId}/credits?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTvCredits(@Path("tvId") tvId: String) : Response<TvCredits>

    @GET("3/tv/{tvId}?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTvDetail(@Path("tvId") tvId: String) : Response<TvDetail>

    @GET("3/search/tv?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getSearchTvData(@Query("query") query: String) : Response<Tv>

    @GET("3/tv/on_the_air?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getUpcomingTv() : Response<Tv>

    @GET("3/tv/{tvId}/similar?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getSimilarTv(@Path("tvId") tvId: String) : Response<Tv>

    @GET("3/trending/tv/week?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTrendigTv() : Response<Tv>

    @GET("3/discover/tv?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTvByGenres(
        @Query("with_genres") genres: String, // It will require comma separated string of genres ids
        @Query("sort_by") sortBy: String? = "popularity.desc"): Response<Tv>





    @GET("3/search/person?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getSearchActorData(@Query("query") query: String) : Response<Actors>

    @GET("3/trending/person/week?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getTrendigActor() : Response<Actors>

    @GET("3/person/{personId}?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getActorDetail(@Path("personId") personId: String) : Response<ActorDetail>

    @GET("3/person/{personId}/combined_credits?api_key=0d78a49b1a3056a1df36e1de7787fcda")
    suspend fun getActorCredits(@Path("personId") personId: String) : Response<ActorCredits>


}