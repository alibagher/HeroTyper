package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.phatphoophoo.pdtran.herotyper.R
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.widget.ImageView


class ScrollingBGView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(
        context,
        attrs,
        attributeSetId
    )

    val animator: ValueAnimator

    init {
        inflate(context, R.layout.scrolling_background_layout, this)

        // Set up the star_background images to scroll
        val backgroundOne = findViewById<ImageView>(R.id.background_one)
        val backgroundTwo = findViewById<ImageView>(R.id.background_two)

        animator = ValueAnimator.ofFloat(0.0f, 1.0f)
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000L

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            val height = backgroundOne.height
            val translationY = height * progress
            backgroundOne.translationY = translationY
            backgroundTwo.translationY = translationY - height
        }
        animator.start()
    }
}