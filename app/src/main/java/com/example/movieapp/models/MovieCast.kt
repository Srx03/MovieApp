package com.example.movieapp.models

data class MovieCast(
    val adult: Boolean?,
    val cast_id: Int?,
    val character: String?,
    val credit_id: String?,
    val gender: Int?,
    val id: Int,
    val known_for_department: String?,
    val name: String?,
    val order: Int?,
    val original_name: String?,
    val popularity: Double?,
    var profile_path: String?
)