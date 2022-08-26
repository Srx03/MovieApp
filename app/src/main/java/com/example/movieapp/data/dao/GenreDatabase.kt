package com.example.movieapp.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GenreData::class], version = 1)
abstract class GenreDatabase: RoomDatabase() {

    abstract fun genreDao(): GenreDao

    companion object{
        @Volatile
        private var INSTANCE: GenreDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK){
            INSTANCE ?: createDatabase(context).also { INSTANCE = it}
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            GenreDatabase::class.java,
            "genre.db"
        ).build()

    }

}