package com.example.data.repositories

import com.example.data.local.CoursesDatabase
import com.example.data.network.CoursesApi
import com.example.data.network.apiRequestFlow
import com.example.data.toCourse
import com.example.data.toCourseEntity
import com.example.domain.models.ApiResponse
import com.example.domain.models.Course
import com.example.domain.repositories.CoursesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val coursesApi: CoursesApi,
    private val coursesDatabase: CoursesDatabase
) : CoursesRepository {
    override suspend fun getCourses(): Flow<ApiResponse<List<Course>>> {
        val apiRequestFlow = apiRequestFlow {
            coursesApi.getCourses()
        }.map { apiResponse ->
            apiResponse.map { result -> result.courses.map { it.toCourse() } }
        }
        val database =
            coursesDatabase.coursesDao().getAll().map { list -> list.map { it.toCourse() } }
        val combined = apiRequestFlow.combine(database, { api, favorites ->
            api.map { list -> list.map { course -> course.copy(hasLike = favorites.any { it.id == course.id }) } }
        })
        return combined
    }

    override suspend fun saveCourseToFavorites(course: Course) {
        val courseEntity = course.toCourseEntity().copy(hasLike = true)
        coursesDatabase.coursesDao().insertAll(courseEntity)
    }

    override suspend fun deleteCourseFromFavorites(course: Course) {
        coursesDatabase.coursesDao().delete(course.toCourseEntity())
    }

    override suspend fun getFavoriteCourses(): Flow<ApiResponse<List<Course>>> =
        coursesDatabase.coursesDao().getAll().map { list -> list.map { it.toCourse() } }
            .map { ApiResponse.Success(it) }

}