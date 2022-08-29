package com.example.movieapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGenre(genre: GenreData)

    @Insert
    suspend fun addAllGenres(objects: List<GenreData>)

    @Query("SELECT * FROM genres_table")
    suspend fun readAllData(): LiveData<List<GenreData>>


}