package com.example.movieapp.models.movie

data class MovieResult(
    val adult: Boolean,
    var backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    var overview: String?,
    val popularity: Double,
    var poster_path: String?,
    var release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)