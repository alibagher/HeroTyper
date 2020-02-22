package com.phatphoophoo.pdtran.herotyper.objects

interface GameObject {
    var position : Pair<Int,Int>
    val direction : Direction
    val velocity : Float

    fun updatePosition()
}

enum class Direction {
    UP, DOWN, NONE
}