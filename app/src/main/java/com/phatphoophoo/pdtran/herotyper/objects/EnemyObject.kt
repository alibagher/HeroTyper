package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class EnemyObject(
    override var position: Pair<Float, Float>,
    override var velocity: Float
) : GameObject() {
    abstract val scoreValue: Int

    override fun updatePosition() {
        this.position = Pair(position.first, position.second + velocity)
    }
}

class BasicEnemy(
    override var position: Pair<Float, Float>,
    override var velocity: Float
) : EnemyObject(position, velocity) {
    override val bitmapResId: Int = R.drawable.meteor
    override val height: Float = (200 + Math.random() * 300).toFloat()
    override val width: Float = this.height
    override val scoreValue = 100
}
