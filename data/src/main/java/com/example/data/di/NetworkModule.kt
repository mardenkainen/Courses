package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.CoursesDatabase
import com.example.data.network.CoursesApi
import com.example.data.repositories.CoursesRepositoryImpl
import com.example.domain.repositories.CoursesRepository
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
object NetworkModule {
    @Provides
    @Singleton
    fun provideCoursesRepository(
        coursesApi: CoursesApi,
        coursesDatabase: CoursesDatabase
    ): CoursesRepository =
        CoursesRepositoryImpl(coursesApi = coursesApi, coursesDatabase = coursesDatabase)

    @Provides
    @Singleton
    fun provideCoursesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        CoursesDatabase::class.java, "database"
    ).build()

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideCoursesApiService(retrofit: Retrofit.Builder): CoursesApi =
        retrofit
            .build()
            .create(CoursesApi::class.java)

}