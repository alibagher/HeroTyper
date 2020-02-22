package com.phatphoophoo.pdtran.herotyper.objects

interface GameObject {
    var position: Pair<Float, Float>
    val direction : Direction
    var velocity : Float

    val width : Float
    val height : Float

    fun updatePosition()
}

enum class Direction {
    UP, DOWN, NONE
}