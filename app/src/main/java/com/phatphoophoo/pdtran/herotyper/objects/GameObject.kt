package com.phatphoophoo.pdtran.herotyper.objects

interface GameObject {
    var velocity : Float
    val bitmapResId : Int

    var position: Pair<Float, Float>
    val width : Float
    val height : Float

    fun updatePosition()
}