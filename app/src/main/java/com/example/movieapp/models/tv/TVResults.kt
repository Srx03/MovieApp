package com.example.movieapp.models.tv

data class TVResults (
    var backdrop_path: String?,
    var first_air_date: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    var overview: String?,
    val popularity: Double,
    var poster_path: String?,
    val vote_average: Double,
    val vote_count: Int
    )