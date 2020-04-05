package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class HealthGainObject: GameObject() {
    abstract val scoreValue: Int

    override fun updateState(): List<HealthGainObject> {
        if(isDestroyed) {
            return onObjectDestroyed()
        }

        this.position = Pair(position.first, position.second + velocity)
        return listOf(this)
    }

    open fun onObjectDestroyed(): List<HealthGainObject> {
        return listOf(
            DestroyedHealthGainObject(height, width, position)
        )
    }
}

class BasicHealthGainObject(
    xPos: Float,
    yPos: Float
): HealthGainObject() {
    override val velocity = 0f
    override var bitmapResId: Int = R.drawable.ufo_green
    override val height: Float = (200 + Math.random() * 100).toFloat()
    override val width: Float = this.height
    override var position: Pair<Float, Float> = Pair(xPos, yPos)
    override val scoreValue = 0

    override fun updateState(): List<HealthGainObject> {
        if(isDestroyed) {
            return onObjectDestroyed()
        }
        //TODO increase/decrease height/width here

        return listOf(this)
    }
}

// Does nothing but animate
class DestroyedHealthGainObject(
    override var height: Float,
    override var width: Float,
    override var position: Pair<Float, Float>
): HealthGainObject() {
    override val velocity = 0f
    override val scoreValue = 0
    override var bitmapResId: Int = R.drawable.explosion_1
    override var isDestroyed = true
}