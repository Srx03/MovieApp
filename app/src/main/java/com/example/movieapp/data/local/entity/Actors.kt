package com.example.movieapp.data.local.entity

data class Actors(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)