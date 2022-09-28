package com.example.movieapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.actor.ActorCredits
import com.example.movieapp.models.actor.ActorDetail
import com.example.movieapp.data.remote.RetrofitRepostory
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ActorViewModel@Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    private val _actorDetailList = MutableLiveData<Resource<ActorDetail>>()
    val actorDetailList: LiveData<Resource<ActorDetail>> = _actorDetailList


    val _actorCreditsList = MutableLiveData<Resource<ActorCredits>>()
    val actorCreditsList: LiveData<Resource<ActorCredits>> = _actorCreditsList


    fun getActorCredits(personId: String) = viewModelScope.launch {
        _actorCreditsList.postValue(Resource.Loading())
        _actorCreditsList.postValue(retrofitRepostory.getActorCredits(personId))
    }

    fun getActorDetail(personId: String) = viewModelScope.launch {
        _actorDetailList.postValue(Resource.Loading())
        _actorDetailList.postValue(retrofitRepostory.getActorDetail(personId))
    }


}