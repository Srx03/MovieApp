package com.example.movieapp.models.actor

data class Actors(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)