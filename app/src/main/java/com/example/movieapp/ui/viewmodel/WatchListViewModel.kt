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
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage

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


}