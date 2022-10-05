package com.example.movieapp.ui.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.firebase.movie.MovieFirebase
import com.example.movieapp.data.firebase.movie.WatchList
import com.example.movieapp.data.firebase.tv.TvFirebase
import com.example.movieapp.data.firebase.tv.TvWatchList
import com.example.movieapp.util.Resource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):ViewModel() {

    val currentUid = firebaseAuth.currentUser?.uid.toString()

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState
    private val _errorState = MutableLiveData<String>()
    val errorState = _errorState

    private  var _movieList = MutableLiveData<ArrayList<WatchList>>()
    val movieList = _movieList

    private val _loadingStateTv = MutableLiveData<Boolean>()
    val loadingStateTv = _loadingStateTv
    private val _errorStateTv = MutableLiveData<String>()
    val errorStateTv = _errorStateTv

    private  var _tvList = MutableLiveData<ArrayList<TvWatchList>>()
    val tvList = _tvList


    private  var _movieDelete = MutableLiveData<Resource<WatchList>>()
    val movieDelete = _movieDelete

    private  var _tvDelete = MutableLiveData<Resource<TvWatchList>>()
    val tvDelete = _tvDelete


    fun getWatchListMovie(){

        _loadingState.value = true

        firestore.collection("watchlist").whereEqualTo("uid",currentUid)
            .addSnapshotListener {snapshot, exeption ->

                if (exeption != null){
                    _loadingState.value = false
                    _errorState.value = exeption.localizedMessage

                }else{

                if (!snapshot!!.isEmpty){
                    val documentList = snapshot.documents

                    for (document in documentList) {
                        val movieList: MovieFirebase? = document.toObject(MovieFirebase::class.java)

                        _movieList.value = movieList?.movie

                        Log.d("dataRead", _movieList.value.toString())
                     }

                    }
                }
            }
    }

    fun getWatchListTv(){
        _loadingStateTv.value = true

        firestore.collection("watchlist").whereEqualTo("uid",currentUid)
            .addSnapshotListener {snapshot, exeption ->

                if (exeption != null){
                    _loadingStateTv.value = false
                    _errorStateTv.value = exeption.localizedMessage

                }else{
                    if (!snapshot!!.isEmpty){
                        val documentList = snapshot.documents

                        for (document in documentList) {
                            val tvList: TvFirebase? = document.toObject(TvFirebase::class.java)

                            _tvList.value = tvList?.tv

                        }

                    }
                }
            }
    }

    fun deleteTv(tvWatchList: TvWatchList){

        val map = mutableMapOf<String, Any>()
        map["tvId"] = tvWatchList.tvId!!
        map["posterPath"] = tvWatchList.posterPath!!
        map["title"] = tvWatchList.title!!
        map["voteAverage"] = tvWatchList.voteAverage!!


        firestore.collection("watchlist")
            .document(currentUid)
            .update("tv",FieldValue.arrayRemove(map))
            .addOnSuccessListener {
                _tvDelete.value = Resource.Success(tvWatchList)
                Log.d("succes",map.toString())
            }
            .addOnFailureListener{
                _tvDelete.value = Resource.Error(it.message.toString())
                Log.d("error",map.toString())
            }
        Log.d("test",map.toString())
    }



    fun deleteMovie(watchList: WatchList){

        val map = mutableMapOf<String, Any>()
        map["movieId"] = watchList.movieId!!
        map["posterPath"] = watchList.posterPath!!
        map["title"] = watchList.title!!
        map["voteAverage"] = watchList.voteAverage!!


        firestore.collection("watchlist")
            .document(currentUid)
            .update("movie",FieldValue.arrayRemove(map))
            .addOnSuccessListener {
                _movieDelete.value = Resource.Success(watchList)
                Log.d("succes",map.toString())
            }
            .addOnFailureListener{
                _movieDelete.value = Resource.Error(it.message.toString())
                Log.d("error",map.toString())
            }
        Log.d("test",map.toString())

    }



}