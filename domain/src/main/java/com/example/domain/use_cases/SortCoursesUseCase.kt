package com.example.domain.use_cases

import com.example.domain.models.Course

class SortCoursesUseCase {
    operator fun invoke(courses: List<Course>): List<Course> {
        return courses.sortedByDescending { it.publishDate }
    }
}