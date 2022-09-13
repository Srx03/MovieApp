package com.example.movieapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.remote.ApiService
import com.example.movieapp.util.Constants.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
class AppModule {



    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRetofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore


    /*@Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext:  Context): LocalDatabase = Room.databaseBuilder(
        appContext,
        LocalDatabase::class.java,
        "movie.db"
    ).fallbackToDestructiveMigration().build()*/


}