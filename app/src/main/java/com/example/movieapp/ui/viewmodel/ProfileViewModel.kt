package com.example.movieapp.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.util.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

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


    private val _editEmail = MutableStateFlow<Resource<String>>(Resource.Unspecified())
    val editEmail : Flow<Resource<String>> = _editEmail

    private val _editPassword = MutableStateFlow<Resource<String>>(Resource.Unspecified())
    val editPassword : Flow<Resource<String>> = _editPassword

    private val _editUsername = MutableStateFlow<Resource<String>>(Resource.Unspecified())
    val editUsername : Flow<Resource<String>> = _editUsername


    val currentUid = firebaseAuth.currentUser?.uid.toString()
    val user = firebaseAuth.currentUser

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
        firebaseStorage.reference.child("imagePath").child(currentUid).delete()

        val storage = firebaseStorage.reference.child("imagePath").child(currentUid)
        storage.putFile(pickedImage)
        storage.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()

            firestore.collection(Constants.USER_COLLECTION).document(currentUid)
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


    fun saveEditEmail(email: String) = viewModelScope.launch {

            if(checkValidationEmail(email)) {

                    _editEmail.emit(Resource.Loading())

                firestore.collection(Constants.USER_COLLECTION).document(currentUid)
                    .update(
                        mapOf("email" to email)
                    )
                    .addOnSuccessListener {
                        _editEmail.value = Resource.Success(email)
                        user!!.updateEmail(email)
                    }
                    .addOnFailureListener{
                        _editEmail.value = Resource.Error(it.message.toString())
                    }

            }else{
                val emailFieldsState = EmailFieldsState(
                    validateEditEmail(email)
                )

                _validationEmail.send(emailFieldsState)

            }

    }


    fun saveEditPassword(password: String) = viewModelScope.launch {


        if(checkValidationPassword(password)) {

                _editPassword.emit(Resource.Loading())

            firestore.collection(Constants.USER_COLLECTION).document(currentUid)
                .update(
                    mapOf("password" to password)
                ).addOnSuccessListener {
                    _editPassword.value = Resource.Success(password)
                    user!!.updatePassword(password)
                }
                .addOnFailureListener{
                    _editPassword.value = Resource.Error(it.message.toString())
                }

        }else{
            val passwordFieldsState = PasswordFieldsState(
                validateEditPassword(password)
            )

                _validationPassword.send(passwordFieldsState)

        }
    }


    fun saveEditUser(userName: String) = viewModelScope.launch {

            if(checkValidationUsername(userName)) {


                    _editUsername.emit(Resource.Loading())


                firestore.collection(Constants.USER_COLLECTION).document(currentUid)
                    .update(
                        mapOf(
                            "userName" to userName
                        )
                    ).addOnSuccessListener {
                        _editUsername.value = Resource.Success(userName)
                    }
                    .addOnFailureListener{
                        _editUsername.value = Resource.Error(it.message.toString())
                    }

            }else{
                val usernameFieldsState = UsernameFieldsState(
                    validateEditUser(userName)
                )


                    _validationUsername.send( usernameFieldsState)

            }
    }

    fun logOut(){
        firebaseAuth.signOut()
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