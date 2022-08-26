package com.example.movieapp.data.dao

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GenreRepostory @Inject constructor(
    val genreDao: GenreDao
) {
    val readAllData: LiveData<List<GenreData>>  =  genreDao.readAllData()

    fun addGenre(genreData: GenreData){
        genreDao.addGenre(genreData)
    }

    fun addAllGenres(genreList: List<GenreData>){
        genreDao.addAllGenres(genreList)
    }
}