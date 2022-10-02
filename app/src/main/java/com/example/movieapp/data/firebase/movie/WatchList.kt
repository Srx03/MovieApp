package com.example.movieapp.data.firebase.movie



data class WatchList (
    val movieId: String?,
    val posterPath: String?,
    val title: String?,
    val voteAverage: String?


){
    constructor(): this("", "","","")
}

