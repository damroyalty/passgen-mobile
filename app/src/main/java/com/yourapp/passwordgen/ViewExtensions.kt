package com.yourapp.passwordgen

import android.animation.ValueAnimator
import android.graphics.Color
import android.view.animation.LinearInterpolator
import android.widget.TextView

fun TextView.addGlowAnimation() {
    val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
        interpolator = LinearInterpolator()
        addUpdateListener {
            val value = it.animatedValue as Float
            val alpha = (50 + (205 * value)).toInt()
            setShadowLayer(10f, 0f, 0f, Color.argb(alpha, 0, 255, 0))
        }
    }
    animator.start()
}