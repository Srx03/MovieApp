package com.example.movieapp.models.tv

data class Season(
    val air_date: Any,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: Any,
    val season_number: Int
)