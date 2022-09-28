package com.example.movieapp.data.firebase.user

data class User (
    val email: String,
    val password: String,
    val userName: String,
    var imagePath: String = "",
    var uid: String = ""


){

}


