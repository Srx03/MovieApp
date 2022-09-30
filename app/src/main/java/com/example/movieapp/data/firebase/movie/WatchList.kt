package com.example.movieapp.data.firebase.movie



data class WatchList (
    val id: String,
    val posterPath: String?,
    val title: String?,
    val voteAverage: String?


){
    constructor(): this("", "","","")
}

