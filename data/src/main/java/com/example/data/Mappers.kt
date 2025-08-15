package com.example.data

import com.example.data.local.entities.CourseEntity
import com.example.data.network.models.CourseRemote
import com.example.domain.models.Course
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun CourseRemote.toCourse() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate.toLocalDate(),
    hasLike = hasLike,
    publishDate = publishDate.toLocalDate()
)

fun CourseEntity.toCourse() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate.toLocalDate(),
    hasLike = hasLike,
    publishDate = publishDate.toLocalDate()
)

fun Course.toCourseEntity() = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate.format(dateFormatter),
    hasLike = hasLike,
    publishDate = publishDate.format(dateFormatter)
)

val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

private fun String.toLocalDate() = LocalDate.parse(this, dateFormatter)