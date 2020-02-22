package com.phatphoophoo.pdtran.herotyper.objects

class Enemy(override var position: Pair<Float, Float>, override var velocity: Float) : GameObject {
    override val direction: Direction = Direction.DOWN
    override val height: Float = 30f
    override val width: Float = 30f

    override fun updatePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}