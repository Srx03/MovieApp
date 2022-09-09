package com.example.movieapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.models.actor.ActorCredits
import com.example.movieapp.data.local.entity.ActorDetail
import com.example.movieapp.data.remote.RetrofitRepostory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ActorViewModel@Inject constructor(
    private val retrofitRepostory: RetrofitRepostory
): ViewModel() {

    val actorDetailList: MutableLiveData<ActorDetail> = MutableLiveData()
    val actorCreditsList: MutableLiveData<ActorCredits> = MutableLiveData()


    fun getActorCredits(personId: String) = viewModelScope.launch {
        retrofitRepostory.getActorCredits(personId).let { response ->

            if (response.isSuccessful){
                actorCreditsList.postValue(response.body())
            }else{
                Log.d("ActorCredits", "getActorCredits Error: ${response.code()}")
            }
        }
    }

    fun getActorDetail(personId: String) = viewModelScope.launch {
        retrofitRepostory.getActorDetail(personId).let { response ->

            if (response.isSuccessful){
                actorDetailList.postValue(response.body())
                Log.d("ActorDetail", "Launched")
            }else{
                Log.d("ActorDetail", "getActorDetail Error: ${response.code()}")
            }
        }
    }


}