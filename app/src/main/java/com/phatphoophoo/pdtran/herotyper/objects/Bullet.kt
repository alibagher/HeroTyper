package com.phatphoophoo.pdtran.herotyper.objects

class Bullet(override var position: Pair<Int,Int>, override var velocity: Float) : GameObject {
    override val direction: Direction = Direction.UP

    override fun updatePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}