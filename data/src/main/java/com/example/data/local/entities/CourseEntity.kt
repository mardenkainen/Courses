package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CourseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)