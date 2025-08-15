package com.example.domain.repositories

import com.example.domain.models.ApiResponse
import com.example.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {
    suspend fun getCourses(): Flow<ApiResponse<List<Course>>>

    suspend fun saveCourseToFavorites(course: Course)
    suspend fun deleteCourseFromFavorites(course: Course)

    suspend fun getFavoriteCourses(): Flow<ApiResponse<List<Course>>>
}