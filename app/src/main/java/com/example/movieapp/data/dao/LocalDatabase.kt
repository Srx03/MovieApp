package com.example.movieapp.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GenreData::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun genreDao(): GenreDao


}