package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.models.movie.Movie
import com.example.movieapp.models.tv.Tv
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    private val _genreMovieList = MutableLiveData<Resource<Movie>>()
    val genreMovieList: LiveData<Resource<Movie>> = _genreMovieList

    private val _genreTvList = MutableLiveData<Resource<Tv>>()
    val genreTvList: LiveData<Resource<Tv>> = _genreTvList

    fun getMediaByGenres(
        genresIds: String,
        isMovie: Boolean = true
    ) = viewModelScope.launch {
        if (isMovie) {
            _genreMovieList.postValue(Resource.Loading())
            _genreMovieList.postValue(retrofitRepostory.getMoviesByGenres(genresIds))
        } else{
            _genreTvList.postValue(Resource.Loading())
            _genreTvList.postValue(retrofitRepostory.getTvByGenres(genresIds))
        }

    }

}