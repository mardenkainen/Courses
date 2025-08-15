package com.example.domain.use_cases

import com.example.domain.models.Course
import com.example.domain.repositories.CoursesRepository

class RemoveCourseFromFavoritesUseCase(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(course: Course) {
        return repository.deleteCourseFromFavorites(course)
    }
}