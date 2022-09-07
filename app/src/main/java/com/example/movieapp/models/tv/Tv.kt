package com.example.movieapp.models.tv

data class Tv(
    val page: Int,
    val results: List<TVResults>,
    val total_pages: Int,
    val total_results: Int
)