package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.dao.GenreData
import com.example.movieapp.data.dao.GenreRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    val genreRepostory: GenreRepostory): ViewModel() {

    var allData: MutableLiveData<List<GenreData>> = MutableLiveData()

    init {
        loadRecords()
    }

    fun getRecordObserver(): MutableLiveData<List<GenreData>>{
        return allData
    }

    fun addGenre(genreData: GenreData){
        genreRepostory.addGenre(genreData)
    }

    fun addAllGenre(genreList: List<GenreData>){
        genreRepostory.addAllGenres(genreList)
    }

    private fun loadRecords() {
        val list = genreRepostory.readAllData
        allData.postValue(list.value)
    }

}