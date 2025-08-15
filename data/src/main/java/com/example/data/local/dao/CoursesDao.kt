package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {
    @Query("SELECT * FROM courseentity")
    fun getAll(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg courseEntity: CourseEntity)

    @Delete
    suspend fun delete(courseEntity: CourseEntity)
}