package com.example.movieapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.movieapp.data.dao.GenreDao
import com.example.movieapp.data.dao.LocalDatabase
import com.example.movieapp.di.retrofit.RetrofitServiceInstance
import com.example.movieapp.util.Constants.BASE_URL
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
  fun providesGenreDao(localDatabase: LocalDatabase): GenreDao = localDatabase.genreDao()


    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInstance {
        return retrofit.create(RetrofitServiceInstance::class.java)
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
    fun provideAppDatabase(@ApplicationContext appContext:  Context): LocalDatabase = Room.databaseBuilder(
        appContext,
        LocalDatabase::class.java,
        "movie.db"
    ).fallbackToDestructiveMigration().build()

}