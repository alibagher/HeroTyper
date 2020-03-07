package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class BulletObject(
    override var position: Pair<Float, Float>,
    override var velocity: Float
) : GameObject() {
    override fun updatePosition() {
        this.position = Pair(position.first, position.second - velocity)
    }

    abstract fun onDestroy()
}

class BasicBullet(
    override var position: Pair<Float, Float>,
    override var velocity: Float
) : BulletObject(position, velocity) {
    override val height: Float = 100f
    override val width: Float = 100f
    override val bitmapResId: Int = R.drawable.rocket

    override fun onDestroy() {

    }
}