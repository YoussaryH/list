package com.youssary.listaccount.core

import android.animation.Animator
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class SpringAnimationHelper(
    view: View,
    windowManager: WindowManager,
    animatorListener: Animator.AnimatorListener
) {
    private val springForce: SpringForce
    private val view: View
    private val windowManager: WindowManager
    private val animatorListener: Animator.AnimatorListener
    private val delayMillisecond: Long
    fun animate() {
        Handler().postDelayed({
            view.pivotX = 0.0f
            view.pivotY = 0.0f
            val springAnimation = SpringAnimation(view, DynamicAnimation.ROTATION)
            springForce.dampingRatio = 0.2f
            springForce.stiffness = 50.0f
            springAnimation.spring = springForce
            springAnimation.setStartValue(80.0f)
            springAnimation.addEndListener { animation: DynamicAnimation<*>?, canceled: Boolean, value: Float, velocity: Float ->
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                val height = displayMetrics.heightPixels.toFloat()
                val width = displayMetrics.widthPixels
                view.animate().setStartDelay(1L)
                    .translationXBy(width.toFloat() / 2.toFloat())
                    .translationYBy(height).setListener(animatorListener)
                    .setInterpolator(DecelerateInterpolator(1.0f)).start()
            }
            springAnimation.start()
        }, delayMillisecond)
    }

    init {
        springForce = SpringForce(0.0f)
        this.view = view
        this.windowManager = windowManager
        this.animatorListener = animatorListener
        delayMillisecond = 50L
    }
}