package com.phatphoophoo.pdtran.herotyper.objects

class Enemy(override var position: Pair<Float, Float>, override var velocity: Float) : GameObject {
    override val direction: Direction = Direction.DOWN
    override var height: Float = (200 + Math.random() * 400).toFloat()
    override var width: Float = (200 + Math.random() * 400).toFloat()

    override fun updatePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}