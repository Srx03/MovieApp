package com.example.movieapp.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "genres_table")
data class GenreData (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val genre_id: Int,
    val en_name: String,
    val tr_name: String
    )