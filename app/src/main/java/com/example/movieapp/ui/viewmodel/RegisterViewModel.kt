package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.firebase.User
import com.example.movieapp.util.*
import com.example.movieapp.util.Constants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
   private val firebaseAuth: FirebaseAuth,
   private val firestore: FirebaseFirestore
): ViewModel() {


    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register : Flow<Resource<User>> = _register

    private val _validation  = Channel<RegisterFieldsState>()
    val validation = _validation.receiveAsFlow()






    fun createAccountWithEmailAndPassword(user: User){

        if (checkValidation(user)) {


            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnSuccessListener {
                    it.user?.let {
                        saveUserInfo(it.uid,user)
                    }
                }.addOnFailureListener {

                    _register.value = Resource.Error(it.message.toString())
                }
        }else{

            val registerFieldsState = RegisterFieldsState(
                validateEmail(user.email),
                validatePassword(user.password),
                validateUser(user.userName)
            )

            runBlocking {
                _validation.send(registerFieldsState)
            }

        }
    }

    private fun saveUserInfo(userUid: String, user: User) {

        val hashMap = hashMapOf<String, Any>()
        hashMap["email"] = user.email
        hashMap["userName"] = user.userName
        hashMap["imagePath"] = user.imagePath
        hashMap["uid"] = userUid
        hashMap["password"] = user.password

        val hashMapWatchList = hashMapOf<String, Any>()
        hashMapWatchList["uid"] = userUid

        firestore.collection(USER_COLLECTION)
            .document(userUid)
            .set(hashMap)
            .addOnSuccessListener {
                 _register.value = Resource.Success(user)
                firestore.collection("watchlist")
                    .document(userUid)
                    .set(hashMapWatchList)
            }
            .addOnFailureListener{
                _register.value = Resource.Error(it.message.toString())
            }


    }

    private fun checkValidation(user: User): Boolean {
        val emailValidaiton = validateEmail(user.email)
        val passwordValidation = validatePassword(user.password)
        val userValidation = validateUser(user.userName)
        val shouldRegister = emailValidaiton is RegisterValidation.Succes &&
                passwordValidation is RegisterValidation.Succes && userValidation is RegisterValidation.Succes

        return shouldRegister
    }

}