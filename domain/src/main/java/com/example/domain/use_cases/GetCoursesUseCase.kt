package com.example.domain.use_cases

import com.example.domain.models.ApiResponse
import com.example.domain.models.Course
import com.example.domain.repositories.CoursesRepository
import kotlinx.coroutines.flow.Flow

class GetCoursesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): Flow<ApiResponse<List<Course>>> {
        return repository.getCourses()
    }
}