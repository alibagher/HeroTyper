package com.phatphoophoo.pdtran.herotyper.objects

class Player(override var position: Pair<Float, Float>) : GameObject {
    override val direction = Direction.NONE
    override var velocity: Float = 0f
    override val height: Float = 40f
    override val width: Float = 20f

    override fun updatePosition() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}