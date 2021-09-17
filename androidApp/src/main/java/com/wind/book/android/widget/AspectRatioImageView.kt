package com.wind.book.android.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView
import com.wind.book.android.R

/**
 * Maintains an aspect ratio based on either width or height. Disabled by default.
 * https://gist.github.com/JakeWharton/2856179
 */
// NOTE: These must be kept in sync with the AspectRatioImageView attributes in attrs.xml.
private const val MEASUREMENT_WIDTH = 0
private const val MEASUREMENT_HEIGHT = 1
private const val DEFAULT_ASPECT_RATIO = 1f
private const val DEFAULT_ASPECT_RATIO_ENABLED = true
private const val DEFAULT_DOMINANT_MEASUREMENT = MEASUREMENT_WIDTH

open class AspectRatioImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ShapeableImageView(context, attrs) {
    // height / width
    private var aspectRatio = DEFAULT_ASPECT_RATIO
    private var aspectRatioEnabled: Boolean
    private var dominantMeasurement: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
        val ratio = a.getString(R.styleable.AspectRatioImageView_ari_aspectRatio)
        if (!TextUtils.isEmpty(ratio)) {
            val wh = ratio!!.split(":").toTypedArray()
            if (wh.size == 2) {
                aspectRatio = wh[0].toFloat() / wh[1].toFloat()
            }
        }
        aspectRatioEnabled = a.getBoolean(
            R.styleable.AspectRatioImageView_ari_aspectRatioEnabled,
            DEFAULT_ASPECT_RATIO_ENABLED
        )
        dominantMeasurement = a.getInt(
            R.styleable.AspectRatioImageView_ari_dominantMeasurement,
            DEFAULT_DOMINANT_MEASUREMENT
        )
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!aspectRatioEnabled) return
        val newWidth: Int
        val newHeight: Int
        when (dominantMeasurement) {
            MEASUREMENT_WIDTH -> {
                newWidth = measuredWidth
                newHeight = (newWidth * aspectRatio).toInt()
            }
            MEASUREMENT_HEIGHT -> {
                newHeight = measuredHeight
                newWidth = (newHeight * aspectRatio).toInt()
            }
            else -> throw IllegalStateException("Unknown measurement with ID $dominantMeasurement")
        }
        setMeasuredDimension(newWidth, newHeight)
    }

    /**
     * Get the aspect ratio for this image view.
     */
    fun getAspectRatio(): Float {
        return aspectRatio
    }

    /**
     * Set the aspect ratio for this image view. This will update the view instantly.
     */
    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
        if (aspectRatioEnabled) {
            requestLayout()
        }
    }

    /**
     * Get whether or not forcing the aspect ratio is enabled.
     */
    fun getAspectRatioEnabled(): Boolean {
        return aspectRatioEnabled
    }

    /**
     * set whether or not forcing the aspect ratio is enabled. This will re-layout the view.
     */
    fun setAspectRatioEnabled(aspectRatioEnabled: Boolean) {
        this.aspectRatioEnabled = aspectRatioEnabled
        requestLayout()
    }

    /**
     * Get the dominant measurement for the aspect ratio.
     */
    fun getDominantMeasurement(): Int {
        return dominantMeasurement
    }

    /**
     * Set the dominant measurement for the aspect ratio.
     *
     * @see .MEASUREMENT_WIDTH
     *
     * @see .MEASUREMENT_HEIGHT
     */
    fun setDominantMeasurement(dominantMeasurement: Int) {
        require(!(dominantMeasurement != MEASUREMENT_HEIGHT && dominantMeasurement != MEASUREMENT_WIDTH)) { "Invalid measurement type." }
        this.dominantMeasurement = dominantMeasurement
        requestLayout()
    }
}
