package com.example.movieapp.ui.show

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.firebase.movie.MovieFirebase
import com.example.movieapp.data.firebase.tv.TvWatchList
import com.example.movieapp.data.firebase.movie.WatchList
import com.example.movieapp.data.firebase.tv.TvFirebase

import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.movie.MovieCredits
import com.example.movieapp.models.movie.MovieDetail
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.models.tv.TvCredits
import com.example.movieapp.models.tv.TvDetail
import com.example.movieapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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


    private val _watchlistMovie = MutableStateFlow<Resource<WatchList>>(Resource.Unspecified())
    val watchlistMovie : Flow<Resource<WatchList>> = _watchlistMovie

    private val _watchlistTv = MutableStateFlow<Resource<TvWatchList>>(Resource.Unspecified())
    val watchlistTv : Flow<Resource<TvWatchList>> = _watchlistTv

    private  var _movieExist = MutableLiveData<Boolean>()
    val movieExist = _movieExist

    private  var _tvExist = MutableLiveData<Boolean>()
    val tvExist = _tvExist

    val currentUid = firebaseAuth.currentUser?.uid.toString()


    private var movieArrayList: ArrayList<WatchList>? = null
    private var tvArrayList: ArrayList<TvWatchList>? = null







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




    fun saveMovie(movieWatchList: WatchList) {

        if (checkIfMovieExist(movieWatchList)){

            _movieExist.value = true

            Log.d("movieExist","already in your watchlist")

        }else {

            _movieExist.value = false
            Log.d("movieExist","Added in your watchlist")
            val map = mutableMapOf<String, Any>()
            map["movieId"] = movieWatchList.movieId!!
            map["posterPath"] = movieWatchList.posterPath!!
            map["title"] = movieWatchList.title!!
            map["voteAverage"] = movieWatchList.voteAverage!!


            firestore.collection("watchlist")
                .document(currentUid)
                .update("movie", FieldValue.arrayUnion(map))
                .addOnSuccessListener {
                    _watchlistMovie.value = Resource.Success(movieWatchList)
                }
                .addOnFailureListener {
                    _watchlistMovie.value = Resource.Error(it.message.toString())
                }

        }
    }


    fun checkIfMovieExist(movieWatchList: WatchList): Boolean{

        firestore.collection("watchlist").whereEqualTo("uid",currentUid)
            .addSnapshotListener {snapshot, _ ->

                    if (!snapshot!!.isEmpty){
                        val documentList = snapshot.documents

                        for (document in documentList) {
                            val movieList: MovieFirebase? = document.toObject(MovieFirebase::class.java)

                            movieArrayList = movieList?.movie

                            Log.d("dataRead1",  movieArrayList.toString())

                        }
                    }
            }

        if (movieArrayList.isNullOrEmpty()){
            return false
        }else{

            for (movieLoopCheck in movieArrayList!!){

                if (movieWatchList.movieId == movieLoopCheck.movieId) {
                   return true
                }
            }
        }

        return false

    }





    fun saveTv(tvWatchList: TvWatchList) {


        if (checkIfTvExist(tvWatchList)) {

            _tvExist.value = true

            Log.d("movieExist", "already in your watchlist")

        } else {

            _tvExist.value = false

            val map = mutableMapOf<String, Any>()
            map["tvId"] = tvWatchList.tvId!!
            map["posterPath"] = tvWatchList.posterPath!!
            map["title"] = tvWatchList.title!!
            map["voteAverage"] = tvWatchList.voteAverage!!


            firestore.collection("watchlist")
                .document(currentUid)
                .update("tv", FieldValue.arrayUnion(map))
                .addOnSuccessListener {
                    _watchlistTv.value = Resource.Success(tvWatchList)
                }
                .addOnFailureListener {
                    _watchlistTv.value = Resource.Error(it.message.toString())
                }
        }


    }


    fun checkIfTvExist(tvWatchList: TvWatchList): Boolean{

        firestore.collection("watchlist").whereEqualTo("uid",currentUid)
            .addSnapshotListener {snapshot, _ ->

                if (!snapshot!!.isEmpty){
                    val documentList = snapshot.documents

                    for (document in documentList) {
                        val tvList: TvFirebase? = document.toObject(TvFirebase::class.java)

                        tvArrayList = tvList?.tv

                        Log.d("dataRead1",  tvArrayList.toString())

                    }
                }
            }

        if (tvArrayList.isNullOrEmpty()){
            return false
        }else{

            for (tvLoopCheck in tvArrayList!!){

                if (tvWatchList.tvId == tvLoopCheck.tvId) {
                    return true
                }
            }
        }

        return false

    }




}