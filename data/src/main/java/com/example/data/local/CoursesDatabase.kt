package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.CoursesDao
import com.example.data.local.entities.CourseEntity

@Database(entities = [CourseEntity::class], version = 1)
abstract class CoursesDatabase: RoomDatabase() {
    abstract fun coursesDao():  CoursesDao
}