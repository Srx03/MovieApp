package com.example.movieapp.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.firebase.entities.User
import com.example.movieapp.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage
): ViewModel() {
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

    private val _loadingStateEdit = MutableLiveData<Boolean>()
    val loadingStateEdit = _loadingStateEdit
    private val _errorStateEdit = MutableLiveData<String>()
    val errorStateEdit = _errorStateEdit

    private val _validationEmail  = Channel<EmailFieldsState>()
    val validationEmail = _validationEmail.receiveAsFlow()

    private val _validationPassword  = Channel<PasswordFieldsState>()
    val validationPassword = _validationPassword.receiveAsFlow()

    private val _validationUsername  = Channel<UsernameFieldsState>()
    val validationUsername  = _validationUsername .receiveAsFlow()


    val currentUid = firebaseAuth.currentUser?.uid.toString()

    fun getDataFromFirebase(){

        _loadingState.value = true



        firestore.collection("user").whereEqualTo("uid", currentUid)
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

    fun saveImage(pickedImage: Uri){
        var currentUserId = firebaseAuth.currentUser?.uid.toString()
        firebaseStorage.reference.child("imagePath").child(currentUserId).delete()

        val storage = firebaseStorage.reference.child("imagePath").child(currentUserId)
        storage.putFile(pickedImage)
        storage.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()

            firestore.collection(Constants.USER_COLLECTION).document(currentUserId)
                .update(
                    mapOf(
                        "imagePath" to imageUrl
                    )
                ).addOnCompleteListener { _loadingStateEdit.value = false }
                .addOnFailureListener { error ->
                    _loadingStateEdit.value = false
                    _errorStateEdit.value = error.localizedMessage
                }

        }
    }

    fun saveEdit(password: String, email: String, userName: String) {


        //check for email (if user pass empty email nothing will happend)
        if (email != null){
            if(checkValidationEmail(email)) {

                var currentUserId = firebaseAuth.currentUser?.uid.toString()
                firestore.collection(Constants.USER_COLLECTION).document(currentUserId)
                    .update(
                        mapOf(
                            "email" to email,
                        )
                    ).addOnCompleteListener { _loadingStateEdit.value = false }
                    .addOnFailureListener { error ->
                        _loadingStateEdit.value = false
                        _errorStateEdit.value = error.localizedMessage
                    }
            }else{
                val emailFieldsState = EmailFieldsState(
                    validateEditEmail(email)
                )

                runBlocking {
                    _validationEmail.send(emailFieldsState)
                }
            }
        }


        if (password != null){
            if(checkValidationPassword(password)) {

                var currentUserId = firebaseAuth.currentUser?.uid.toString()
                firestore.collection(Constants.USER_COLLECTION).document(currentUserId)
                    .update(
                        mapOf(
                            "password" to password,
                        )
                    ).addOnCompleteListener { _loadingStateEdit.value = false }
                    .addOnFailureListener { error ->
                        _loadingStateEdit.value = false
                        _errorStateEdit.value = error.localizedMessage
                    }
            }else{
                val passwordFieldsState = PasswordFieldsState(
                    validateEditPassword(password)
                )

                runBlocking {
                    _validationPassword.send(passwordFieldsState)
                }
            }
        }

        if (userName != null){
            if(checkValidationUsername(userName)) {

                var currentUserId = firebaseAuth.currentUser?.uid.toString()
                firestore.collection(Constants.USER_COLLECTION).document(currentUserId)
                    .update(
                        mapOf(
                            "userName" to userName,
                        )
                    ).addOnCompleteListener { _loadingStateEdit.value = false }
                    .addOnFailureListener { error ->
                        _loadingStateEdit.value = false
                        _errorStateEdit.value = error.localizedMessage
                    }
            }else{
                val usernameFieldsState = UsernameFieldsState(
                    validateEditUser(userName)
                )

                runBlocking {
                    _validationUsername.send( usernameFieldsState)
                }
            }
        }









    }

    private fun checkValidationEmail(email: String): Boolean {
        val emailValidaiton = validateEditEmail(email)
        val shouldEdit = emailValidaiton is RegisterValidation.Succes
        return shouldEdit
    }

    private fun checkValidationPassword(password: String): Boolean {
        val passwordValidation = validateEditPassword(password)
        val shouldEdit = passwordValidation is RegisterValidation.Succes

        return shouldEdit
    }

    private fun checkValidationUsername(userName: String): Boolean {
        val userValidation = validateEditUser(userName)
        val shouldEdit =userValidation is RegisterValidation.Succes

        return shouldEdit
    }


}