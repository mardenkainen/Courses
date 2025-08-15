package com.example.presentation.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView

class TopCropImage : ShapeableImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        val matrix = imageMatrix
        val scaleFactor = (r-l).toFloat() / drawable.intrinsicWidth.toFloat()
        matrix.setScale(scaleFactor, scaleFactor)
        imageMatrix = matrix
        return super.setFrame(l, t, r, b)
    }
}