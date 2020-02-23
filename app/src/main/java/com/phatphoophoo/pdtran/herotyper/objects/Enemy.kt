package com.phatphoophoo.pdtran.herotyper.objects

class Enemy(
    override var position: Pair<Float, Float>,
    override var velocity: Float
) : GameObject {
    override val direction: Direction = Direction.DOWN
    override val height: Float = (200 + Math.random() * 300).toFloat()
    override val width: Float = this.height

    val scoreValue = 100

    override fun updatePosition() {
        this.position = Pair(position.first, position.second + velocity)
    }

}