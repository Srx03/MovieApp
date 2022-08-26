package com.example.movieapp.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.GenreDatabase
import com.example.movieapp.di.retrofit.RetrofitServiceInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

var baseURL = "https://api.themoviedb.org/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  /*  fun getappDB(context: Application):  GenreDatabase{
        return GenreDatabase.c
    }*/


    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInstance {
        return retrofit.create(RetrofitServiceInstance::class.java)
    }

    @Singleton
    @Provides
    fun getRetofitInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext:  Context): GenreDatabase = Room.databaseBuilder(
        appContext,
        GenreDatabase::class.java,
        "genre.db"
    ).fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun getDao(appDB: GenreDatabase): GenreDao{
        return appDB.genreDao()
    }

}