package com.youssary.listaccount.core

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import com.youssary.listaccount.R

@Suppress("DEPRECATION", "NAME_SHADOWING")
class AnimatedLoadingIndicator : View {
    private var mStartTime: Long = -1
    private var mPostedHide = false
    private var mPostedShow = false
    private var mDismissed = false
    private val mDelayedHide = Runnable {
        mPostedHide = false
        mStartTime = -1
        visibility = GONE
    }
    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis()
            visibility = VISIBLE
        }
    }
    var mMinWidth = 0
    var mMaxWidth = 0
    var mMinHeight = 0
    private var mMaxHeight = 0
    private var mIndicator: Indicator? = null
    private var mIndicatorColor = 0
    private var mShouldStartAnimationDrawable = false

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0, R.style.AnimatedLoadingIndicatorView)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, R.style.AnimatedLoadingIndicatorView)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, R.style.AnimatedLoadingIndicatorView)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        mMinWidth = 24
        mMaxWidth = 48
        mMinHeight = 24
        mMaxHeight = 48
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.AnimatedLoadingIndicatorView,
            defStyleAttr,
            defStyleRes
        )
        mMinWidth =
            a.getDimensionPixelSize(R.styleable.AnimatedLoadingIndicatorView_minWidth, mMinWidth)
        mMaxWidth =
            a.getDimensionPixelSize(R.styleable.AnimatedLoadingIndicatorView_maxWidth, mMaxWidth)
        mMinHeight =
            a.getDimensionPixelSize(R.styleable.AnimatedLoadingIndicatorView_minHeight, mMinHeight)
        mMaxHeight =
            a.getDimensionPixelSize(R.styleable.AnimatedLoadingIndicatorView_maxHeight, mMaxHeight)
        val indicatorName =
            a.getString(R.styleable.AnimatedLoadingIndicatorView_indicatorName)
        mIndicatorColor = a.getColor(
            R.styleable.AnimatedLoadingIndicatorView_indicatorColor,
            Color.WHITE
        )
        setIndicator(indicatorName)
        if (mIndicator == null) {
            indicator = DEFAULT_INDICATOR
        }
        a.recycle()
    }

    var indicator: Indicator?
        get() = mIndicator
        set(d) {
            if (mIndicator !== d) {
                if (mIndicator != null) {
                    mIndicator!!.callback = null
                    unscheduleDrawable(mIndicator)
                }
                mIndicator = d
                setIndicatorColor(mIndicatorColor)
                if (d != null) {
                    d.callback = this
                }
                postInvalidate()
            }
        }

    private fun setIndicatorColor(color: Int) {
        mIndicatorColor = color
        mIndicator!!.color = color
    }

    private fun setIndicator(indicatorName: String?) {
        if (TextUtils.isEmpty(indicatorName)) {
            return
        }
        val drawableClassName = StringBuilder()
        if (!indicatorName!!.contains(".")) {
            val defaultPackageName = javaClass.getPackage()!!.name
            drawableClassName.append(defaultPackageName)
                .append(".indicators")
                .append(".")
        }
        drawableClassName.append(indicatorName)
        try {
            val drawableClass =
                Class.forName(drawableClassName.toString())
            var indicator =
                drawableClass.newInstance() as Indicator
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun smoothToShow() {
        startAnimation(
            AnimationUtils.loadAnimation(
                context,
                android.R.anim.fade_in
            )
        )
        visibility = VISIBLE
    }

    fun smoothToHide() {
        startAnimation(
            AnimationUtils.loadAnimation(
                context,
                android.R.anim.fade_out
            )
        )
        visibility = GONE
    }

    fun hide() {
        mDismissed = true
        removeCallbacks(mDelayedShow)
        val diff = System.currentTimeMillis() - mStartTime
        if (diff >= MIN_SHOW_TIME || mStartTime == -1L) {
            visibility = GONE
        } else {
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME - diff)
                mPostedHide = true
            }
        }
    }

    fun show() {
        mStartTime = -1
        mDismissed = false
        removeCallbacks(mDelayedHide)
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY.toLong())
            mPostedShow = true
        }
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return who === mIndicator || super.verifyDrawable(who)
    }

    fun startAnimation() {
        if (visibility != VISIBLE) {
            return
        }
        if (mIndicator != null) {
            mShouldStartAnimationDrawable = true
        }
        postInvalidate()
    }

    fun stopAnimation() {
        if (mIndicator != null) {
            mIndicator!!.stop()
            mShouldStartAnimationDrawable = false
        }
        postInvalidate()
    }

    override fun setVisibility(v: Int) {
        if (visibility != v) {
            super.setVisibility(v)
            if (v == GONE || v == INVISIBLE) {
                stopAnimation()
            } else {
                startAnimation()
            }
        }
    }

    override fun onVisibilityChanged(
        changedView: View,
        visibility: Int
    ) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == GONE || visibility == INVISIBLE) {
            stopAnimation()
        } else {
            startAnimation()
        }
    }

    override fun invalidateDrawable(dr: Drawable) {
        if (verifyDrawable(dr)) {
            val dirty = dr.bounds
            val scrollX = scrollX + paddingLeft
            val scrollY = scrollY + paddingTop
            invalidate(
                dirty.left + scrollX,
                dirty.top + scrollY,
                dirty.right + scrollX,
                dirty.bottom + scrollY
            )
        } else {
            super.invalidateDrawable(dr)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        updateDrawableBounds(w, h)
    }

    fun updateDrawableBounds(w: Int, h: Int) {
        var w = w
        var h = h
        w -= paddingRight + paddingLeft
        h -= paddingTop + paddingBottom
        var right = w
        var bottom = h
        var top = 0
        var left = 0
        if (mIndicator != null) {
            val intrinsicWidth = mIndicator!!.intrinsicWidth
            val intrinsicHeight = mIndicator!!.intrinsicHeight
            val intrinsicAspect = intrinsicWidth.toFloat() / intrinsicHeight
            val boundAspect = w.toFloat() / h
            if (intrinsicAspect != boundAspect) {
                if (boundAspect > intrinsicAspect) {
                    val width = (h * intrinsicAspect).toInt()
                    left = (w - width) / 2
                    right = left + width
                } else {
                    val height = (w * (1 / intrinsicAspect)).toInt()
                    top = (h - height) / 2
                    bottom = top + height
                }
            }
            mIndicator!!.setBounds(left, top, right, bottom)
        }
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTrack(canvas)
    }

    fun drawTrack(canvas: Canvas) {
        val d: Drawable? = mIndicator
        if (d != null) {
            val saveCount = canvas.save()
            canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
            d.draw(canvas)
            canvas.restoreToCount(saveCount)
            if (mShouldStartAnimationDrawable) {
                (d as Animatable).start()
                mShouldStartAnimationDrawable = false
            }
        }
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var dw = 0
        var dh = 0
        val d: Drawable? = mIndicator
        if (d != null) {
            dw = Math.max(mMinWidth, Math.min(mMaxWidth, d.intrinsicWidth))
            dh = Math.max(
                mMinHeight,
                Math.min(mMaxHeight, d.intrinsicHeight)
            )
        }
        updateDrawableState()
        dw += paddingLeft + paddingRight
        dh += paddingTop + paddingBottom
        val measuredWidth = resolveSizeAndState(dw, widthMeasureSpec, 0)
        val measuredHeight = resolveSizeAndState(dh, heightMeasureSpec, 0)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        updateDrawableState()
    }

    private fun updateDrawableState() {
        val state = drawableState
        if (mIndicator != null && mIndicator!!.isStateful) {
            mIndicator!!.state = state
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        if (mIndicator != null) {
            mIndicator!!.setHotspot(x, y)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
        removeCallbacks()
    }

    override fun onDetachedFromWindow() {
        stopAnimation()
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(mDelayedHide)
        removeCallbacks(mDelayedShow)
    }

    companion object {
        private val DEFAULT_INDICATOR =
            ProgressBallMultipleIndicator()
        private const val MIN_SHOW_TIME = 500 // ms
        private const val MIN_DELAY = 500 // ms
    }
}