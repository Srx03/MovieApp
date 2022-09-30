package com.example.movieapp.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.firebase.movie.MovieFirebase
import com.example.movieapp.data.firebase.movie.WatchList
import com.example.movieapp.data.firebase.tv.TvFirebase
import com.example.movieapp.data.firebase.tv.TvWatchList
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.MovieDetail
import com.example.movieapp.models.tv.TvDetail
import com.example.movieapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

                        Log.d("dataRead", _movieList.toString())
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

    fun deleteTv(itemId: Int){
        val array: String = "tv/$itemId"
        firestore.collection("watchlist").document(currentUid).update("tv", FieldValue.arrayRemove(array))
    }

    fun deleteMovie(itemId: Int){
        val array: String = "movie/$itemId"
        firestore.collection("watchlist").document(currentUid).update("movie", FieldValue.arrayRemove(array))
        Log.d("test",itemId.toString())

    }



}