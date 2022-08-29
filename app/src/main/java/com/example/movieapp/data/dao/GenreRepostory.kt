package com.example.movieapp.data.dao

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GenreRepostory @Inject constructor(
    val genreDao: GenreDao
) {

    suspend fun readAllData(): LiveData<List<GenreData>> = genreDao.readAllData()

    suspend fun addGenre(genreData: GenreData){
        genreDao.addGenre(genreData)
    }

    suspend fun addAllGenres(genreList: List<GenreData>){
        genreDao.addAllGenres(genreList)
    }
}