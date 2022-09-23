package com.example.movieapp.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):ViewModel() {

    val currentUid = firebaseAuth.currentUser?.uid.toString()

    private val _nameState = MutableLiveData<String>()
    val nameState = _nameState
    private val _passwordState = MutableLiveData<String>()
    val passwordState = _passwordState
    private val _emailState = MutableLiveData<String>()
    val emailState = _emailState
    private val _imageState = MutableLiveData<String>()
    val imageState = _imageState
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState
    private val _errorState = MutableLiveData<String>()
    val errorState = _errorState


    private var _movieWatchList: ArrayList<String>? = null
    private var _tvWatchList: ArrayList<String>? = null



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

                            val email = document.get("email") as String
                            val user = document.get("userName") as String
                            val downloadUri = document.get("imagePath") as String
                            val password = document.get("password") as String
                            _nameState.value = user
                            _imageState.value = downloadUri
                            _emailState.value = email
                            _passwordState.value = password
                        }

                    }
                }

            }
    }



}