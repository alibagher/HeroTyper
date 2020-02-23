package com.phatphoophoo.pdtran.herotyper.objects

class Enemy(
    override var position: Pair<Float, Float>,
    override var velocity: Float
) : GameObject {
    override val direction: Direction = Direction.DOWN
    override var height: Float = (200 + Math.random() * 400).toFloat()
    override var width: Float = (200 + Math.random() * 400).toFloat()

    val scoreValue = 100

    override fun updatePosition() {
        this.position = Pair(position.first, position.second + velocity)
    }

}