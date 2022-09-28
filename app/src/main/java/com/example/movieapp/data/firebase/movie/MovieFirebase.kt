package com.example.movieapp.data.firebase.movie



data class MovieFirebase(
    val movie: ArrayList<WatchList>


){
    constructor(): this(ArrayList())
}

