package com.phatphoophoo.pdtran.herotyper.objects

class Player(override var position: Pair<Int, Int>) : GameObject {
    override val direction = Direction.NONE
    override val velocity: Float = 0f

    override fun updatePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}