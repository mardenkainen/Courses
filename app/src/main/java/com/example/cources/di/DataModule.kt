package com.example.cources.di

import com.example.domain.repositories.CoursesRepository
import com.example.domain.use_cases.AddCourseToFavoritesUseCase
import com.example.domain.use_cases.GetCoursesUseCase
import com.example.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.domain.use_cases.RemoveCourseFromFavoritesUseCase
import com.example.domain.use_cases.SortCoursesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideGetCoursesUseCase(repository: CoursesRepository) =
        GetCoursesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetFavoriteCoursesUseCase(repository: CoursesRepository) =
        GetFavoriteCoursesUseCase(repository)

    @Provides
    @Singleton
    fun provideAddCourseToFavoritesUseCase(repository: CoursesRepository) =
        AddCourseToFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveCourseFromFavoritesUseCase(repository: CoursesRepository) =
        RemoveCourseFromFavoritesUseCase(repository)

    @Provides
    @Singleton
    fun provideSortCoursesUseCase() =
        SortCoursesUseCase()

}