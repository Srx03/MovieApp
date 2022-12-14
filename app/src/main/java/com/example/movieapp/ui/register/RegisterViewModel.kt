package com.example.movieapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.firebase.user.User
import com.example.movieapp.util.*
import com.example.movieapp.util.Constants.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

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






    fun createAccountWithEmailAndPassword(user: User) = viewModelScope.launch{

        if (checkValidation(user)) {
                _register.emit(Resource.Loading())

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

                _validation.send(registerFieldsState)


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

                firestore.collection("watchlist")
                    .document(userUid)
                    .update("tv",FieldValue.arrayUnion(),"movie", FieldValue.arrayUnion())

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