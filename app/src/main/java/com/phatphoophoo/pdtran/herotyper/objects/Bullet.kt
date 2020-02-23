package com.phatphoophoo.pdtran.herotyper.objects

class Bullet(override var position: Pair<Float, Float>, override var velocity: Float) : GameObject {
    override val direction: Direction = Direction.UP
    override val height: Float = 10f
    override val width: Float = 10f

    override fun updatePosition() {
        this.position = Pair(position.first, position.second - velocity)
    }
}