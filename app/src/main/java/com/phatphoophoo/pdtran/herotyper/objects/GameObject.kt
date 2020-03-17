package com.phatphoophoo.pdtran.herotyper.objects

import android.graphics.Bitmap

abstract class GameObject {
    abstract val velocity : Float
    abstract var bitmapResId : Int
    open var isDestroyed : Boolean = false

    // Must always start out as null, which is why this class
    // isn't an interface
    var bitmap : Bitmap? = null

    abstract var position: Pair<Float, Float>
    abstract val width : Float
    abstract val height : Float

    // Returns a list of objects;
    // could be empty, contain the updated object,
    // or multiple objects
    open fun updateState() : List<GameObject> {
        return if (isDestroyed) emptyList()
        else listOf(this)
    }
}