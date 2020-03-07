package com.phatphoophoo.pdtran.herotyper.objects

import android.graphics.Bitmap

abstract class GameObject {
    abstract var velocity : Float
    abstract val bitmapResId : Int
    var bitmap : Bitmap? = null

    abstract var position: Pair<Float, Float>
    abstract val width : Float
    abstract val height : Float

    abstract fun updatePosition()
}