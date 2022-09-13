package com.example.movieapp.data.firebase.entities

data class User (
    val email: String,
    val userName: String,
    var imagePath: String = ""
){
    constructor(): this("","","")
}