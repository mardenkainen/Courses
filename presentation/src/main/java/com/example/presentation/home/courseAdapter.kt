package com.example.presentation.home

import com.example.domain.models.Course
import com.example.presentation.R
import com.example.presentation.databinding.ItemCourseBinding
import com.example.presentation.formatDate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun courseAdapter(onHasLikeClick: (Course) -> Unit) =
    adapterDelegateViewBinding<Course, Course, ItemCourseBinding>(
        { layoutInflater, root -> ItemCourseBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.apply {
                title.text = item.title
                text.text = item.text
                rating.text = item.rate
                price.text = getString(R.string.price_format, item.price)
                date.text = item.startDate.formatDate(context)
                hasLike.isSelected = item.hasLike
                hasLike.setOnClickListener {
                    onHasLikeClick(item)
                }
            }
        }
    }