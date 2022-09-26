package com.example.movieapp.ui.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
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


    private var _movieWatchList: ArrayList<String>? = null
    val movieWatchList: ArrayList<String>? = _movieWatchList


    private var _tvWatchList: ArrayList<String>? = null
    val tvWatchList: ArrayList<String>? = _tvWatchList


    fun getWatchListMovie(){

        _loadingState.value = true

        firestore.collection("watchlist").whereEqualTo("uid", currentUid)
            .addSnapshotListener{ snapshot, exeption ->

                if (exeption != null){
                    _loadingState.value = false
                    _errorState.value = exeption.localizedMessage

                }else{
                    if (!snapshot!!.isEmpty){
                        val documentList = snapshot.documents

                        for (document in documentList) {
                            _movieWatchList = document.get("movieId") as ArrayList<String>
                        }

                    }
                }

            }
    }


    fun getWatchListTv(){

        _loadingState.value = true

        firestore.collection("watchlist").whereEqualTo("uid", currentUid)
            .addSnapshotListener{ snapshot, exeption ->

                if (exeption != null){
                    _loadingState.value = false
                    _errorState.value = exeption.localizedMessage

                }else{
                    if (!snapshot!!.isEmpty){
                        val documentList = snapshot.documents

                        for (document in documentList) {
                            _tvWatchList = document.get("tvId") as ArrayList<String>
                        }

                    }
                }

            }
    }



}