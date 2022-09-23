package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.movie.MovieCredits
import com.example.movieapp.models.movie.MovieDetail
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.models.tv.TvCredits
import com.example.movieapp.models.tv.TvDetail
import com.example.movieapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ShowViewModel@Inject constructor(
    private val retrofitRepostory: RetrofitRepostory,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _movieCreditsList = MutableLiveData<Resource<MovieCredits>>()
    val movieCreditsList: LiveData<Resource<MovieCredits>> = _movieCreditsList

    private val _similarMovieList = MutableLiveData<Resource<Movie>>()
    val similarMovieList: LiveData<Resource<Movie>> = _similarMovieList

    private val _movieDetailList = MutableLiveData<Resource<MovieDetail>>()
    val movieDetailList: LiveData<Resource<MovieDetail>> = _movieDetailList


    private val _tvCreditsList = MutableLiveData<Resource<TvCredits>>()
    val tvCreditsList: LiveData<Resource<TvCredits>> = _tvCreditsList

    private val _similarTvList = MutableLiveData<Resource<Tv>>()
    val similarTvList: LiveData<Resource<Tv>> = _similarTvList

    private val _tvDetailList = MutableLiveData<Resource<TvDetail>>()
    val tvDetailList: LiveData<Resource<TvDetail>> = _tvDetailList


    private val _watchlistMovie = MutableStateFlow<Resource<String>>(Resource.Unspecified())
    val watchlistMovie : Flow<Resource<String>> = _watchlistMovie

    private val _watchlistTv = MutableStateFlow<Resource<String>>(Resource.Unspecified())
    val watchlistTv : Flow<Resource<String>> = _watchlistTv

    val currentUid = firebaseAuth.currentUser?.uid.toString()

    private val watchListMovieId: ArrayList<String> = ArrayList()
    private val watchListTvId: ArrayList<String> = ArrayList()




    fun getMovieCredits(movieId: String) = viewModelScope.launch {
       _movieCreditsList.postValue(Resource.Loading())
       _movieCreditsList.postValue(retrofitRepostory.getMovieCredits(movieId))
    }

    fun getSimilarMovie(movieId: String) = viewModelScope.launch {
        _similarMovieList.postValue(Resource.Loading())
        _similarMovieList.postValue(retrofitRepostory.getSimilarMovies(movieId))
    }

    fun getMoiveDetail(movieId: String) = viewModelScope.launch {
        _movieDetailList.postValue(Resource.Loading())
        _movieDetailList.postValue(retrofitRepostory.getMovieDetail(movieId))
    }

    fun getTvDetail(tvId: String) = viewModelScope.launch {
        _tvDetailList.postValue(Resource.Loading())
        _tvDetailList.postValue(retrofitRepostory.getTvDetail(tvId))
    }

    fun getTvCredits(tvId: String) = viewModelScope.launch {
        _tvCreditsList.postValue(Resource.Loading())
        _tvCreditsList.postValue(retrofitRepostory.getTvCredits(tvId))
    }

    fun getSimilarTv(tvId: String) = viewModelScope.launch {
        _similarTvList.postValue(Resource.Loading())
        _similarTvList.postValue(retrofitRepostory.getSimialrTv(tvId))
    }




    fun saveMovie(movieId: String) {

        firestore.collection("watchlist")
            .document(currentUid)
            .update("movieId", FieldValue.arrayUnion(movieId))
            .addOnSuccessListener {
                _watchlistMovie.value = Resource.Success(movieId)
            }
            .addOnFailureListener{
                _watchlistMovie.value = Resource.Error(it.message.toString())
            }



    }

    fun saveTv(tvId: String) {
        firestore.collection("watchlist")
            .document(currentUid)
            .update("tvId", FieldValue.arrayUnion(tvId))
            .addOnSuccessListener {
                _watchlistMovie.value = Resource.Success(tvId)
            }
            .addOnFailureListener{
                _watchlistMovie.value = Resource.Error(it.message.toString())
            }
    }





}