package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

class PlayerObject(override var position: Pair<Float, Float>) : GameObject() {
    override val velocity: Float = 0f
    override val height: Float = 200f
    override val width: Float = 200f
    override var bitmapResId: Int = R.drawable.spaceship

    companion object {
        val size = 200f
    }
}