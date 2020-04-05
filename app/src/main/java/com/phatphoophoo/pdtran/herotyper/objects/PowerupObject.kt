package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class PowerupObject(
    override var position: Pair<Float, Float>,
    override val velocity: Float
) : GameObject() {
    override fun updateState(): List<PowerupObject> {
        if (isDestroyed) return emptyList()

        this.position = Pair(position.first, position.second - velocity)
        return listOf(this)
    }
}

class MissilePowerup(
    override var position: Pair<Float, Float>,
    override val velocity: Float
) : PowerupObject(position, velocity) {
    override val height: Float = 100f
    override val width: Float = 100f
    override var bitmapResId: Int = R.drawable.rocket
}
