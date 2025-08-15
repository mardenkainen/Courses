package com.example.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResponse
import com.example.domain.models.Course
import com.example.domain.use_cases.GetFavoriteCoursesUseCase
import com.example.domain.use_cases.RemoveCourseFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val removeCourseFromFavoritesUseCase: RemoveCourseFromFavoritesUseCase
) : ViewModel() {
    private val _courses = MutableStateFlow(listOf<Course>())
    val courses = _courses.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            getFavoriteCoursesUseCase().collect { response ->
                when(response) {
                    is ApiResponse.Failure -> {

                    }
                    ApiResponse.Loading -> {

                    }
                    is ApiResponse.Success<List<Course>> -> {
                        _courses.value = response.data
                    }
                }
            }

        }
    }

    fun removeFromFavorites(course: Course) {
        viewModelScope.launch {
            removeCourseFromFavoritesUseCase(course)
        }
    }
}