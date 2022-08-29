package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.dao.GenreData
import com.example.movieapp.data.dao.GenreRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun addGenre(genreData: GenreData) = viewModelScope.launch{
        genreRepostory.addGenre(genreData)
    }

    fun addAllGenre(genreList: List<GenreData>)= viewModelScope.launch{
        genreRepostory.addAllGenres(genreList)
    }

    private fun loadRecords() = viewModelScope.launch {
        val list = genreRepostory.readAllData()
        allData.postValue(list.value)
    }

}