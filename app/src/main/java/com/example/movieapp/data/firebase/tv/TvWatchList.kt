package com.example.movieapp.data.firebase.tv



data class TvWatchList (
    val tvId: String?,
    val posterPath: String?,
    val title: String?,
    val voteAverage: String?


){
    constructor(): this("", "","","")
}

