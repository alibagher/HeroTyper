package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class HealthGainObject: GameObject() {
    abstract val scoreValue: Int
    abstract var isRewarded: Boolean

    override fun updateState(): List<HealthGainObject> {
        if(isRewarded) {
            return onObjectReward()
        }
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

    open fun onObjectReward(): List<HealthGainObject> {
        return listOf(
            RewardHealthGainObject(height, width, position)
        )
    }
}

class BasicHealthGainObject(
    xPos: Float, yPos: Float
): HealthGainObject() {
    override val velocity = 4.1f
    override var bitmapResId: Int = R.drawable.ufo_green
    override var height: Float = (100).toFloat()
    override var width: Float = this.height
    override var position: Pair<Float, Float> = Pair(xPos, yPos)
    override val scoreValue = 0
    override var isRewarded: Boolean = false

    companion object {
        val BITMAP_RES_LIST: List<Int> = listOf(
            R.drawable.ufo_green,
            R.drawable.ufo_orange,
            R.drawable.ufo_white
        )
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
    override var isRewarded = false

    override fun updateState(): List<HealthGainObject> {
        //TODO update the animations for this object
        return emptyList()
    }
}

// Does nothing but animate
class RewardHealthGainObject(
    override var height: Float,
    override var width: Float,
    override var position: Pair<Float, Float>
): HealthGainObject() {
    override val velocity = 0f
    override val scoreValue = 0
    override var bitmapResId: Int = R.drawable.explosion_4
    override var isDestroyed = true
    override var isRewarded = true

    override fun updateState(): List<HealthGainObject> {
        //TODO update the animations for this object
        return emptyList()
    }
}