package com.example.movieapp.data.firebase

data class User (
    val email: String,
    val password: String,
    val userName: String,
    var imagePath: String = "",
    var uid: String = ""


){

}


