package com.example.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResponse
import com.example.domain.models.Course
import com.example.domain.use_cases.AddCourseToFavoritesUseCase
import com.example.domain.use_cases.GetCoursesUseCase
import com.example.domain.use_cases.RemoveCourseFromFavoritesUseCase
import com.example.domain.use_cases.SortCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coursesUseCase: GetCoursesUseCase,
    private val sortCoursesUseCase: SortCoursesUseCase,
    private val addCourseToFavoritesUseCase: AddCourseToFavoritesUseCase,
    private val removeCourseFromFavoritesUseCase: RemoveCourseFromFavoritesUseCase,
) : ViewModel() {
    private var sortData = MutableStateFlow(false)
    private val _loadingState = MutableStateFlow(LoadingState.LOADING)
    val loadingState = _loadingState.asStateFlow()
    private val _courses = MutableStateFlow(listOf<Course>())
    val courses = _courses.combine(
        sortData,
        { list, bool -> if (bool) sortCoursesUseCase(list) else list })
        .stateIn(viewModelScope, started = WhileSubscribed(500L), initialValue = listOf())

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            coursesUseCase().flowOn(Dispatchers.IO).collectLatest { response ->
                when (response) {
                    is ApiResponse.Failure -> {
                        _loadingState.value = LoadingState.FAILURE
                        _courses.value = listOf()
                    }

                    ApiResponse.Loading -> {
                        _loadingState.value = LoadingState.LOADING
                    }

                    is ApiResponse.Success<List<Course>> -> {
                        _loadingState.value = LoadingState.SUCCESS
                        _courses.value = response.data
                    }
                }
            }
        }
    }

    fun onFavoritesClick(course: Course) {
        if (course.hasLike) removeFromFavorites(course) else addToFavorites(course)
    }

    private fun addToFavorites(course: Course) {
        viewModelScope.launch {
            addCourseToFavoritesUseCase(course)
        }
    }

    private fun removeFromFavorites(course: Course) {
        viewModelScope.launch {
            removeCourseFromFavoritesUseCase(course)
        }
    }

    fun sortCourses() {
        sortData.value = true
    }
}