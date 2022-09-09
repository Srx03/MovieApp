package com.example.movieapp.models.actor

import com.example.movieapp.models.movie.MovieResult

data class ActorCredits(
    val cast: List<MovieResult>,
    val id: Int
)