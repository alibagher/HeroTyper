package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R
import kotlin.random.Random

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
    xPos: Float
): HealthGainObject() {
    override val velocity = 4.1f
    override var bitmapResId: Int = R.drawable.plasma_ball
    override var height: Float = 100F
    override var width: Float = 130F
    override var position: Pair<Float, Float> = Pair(xPos, -height)
    override val scoreValue = 0
    override var isRewarded: Boolean = false
}

// Does nothing but animate
class DestroyedHealthGainObject(
    override var height: Float,
    override var width: Float,
    override var position: Pair<Float, Float>
): HealthGainObject() {
    override val velocity = 0f
    override val scoreValue = 0
    override var bitmapResId: Int = R.drawable.magic_sprite_1
    override var isDestroyed = true
    override var isRewarded = false

    companion object {
        val BITMAP_RES_LIST: List<Int> = listOf(
            R.drawable.magic_sprite_1,
            R.drawable.magic_sprite_2,
            R.drawable.magic_sprite_3,
            R.drawable.magic_sprite_4,
            R.drawable.magic_sprite_5
        )
    }

    val ANIMATION_RATE = 2  // Needed to slow down the animation cycle
    var curAnimationFrame = 1
    var curAnimationWait = 0

    // Move through the animation map
    override fun updateState() : List<HealthGainObject> {
        if (curAnimationFrame >= BITMAP_RES_LIST.size) return emptyList()

        if (curAnimationWait >= ANIMATION_RATE){
            curAnimationWait = 0

            // Advance through animation frames
            bitmapResId = BITMAP_RES_LIST[curAnimationFrame]
            bitmap = null // Reset cache it so it gets redrawn
            curAnimationFrame++
        }

        curAnimationWait++

        return listOf(this)
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
    override var bitmapResId: Int = R.drawable.magic_sprite_1
    override var isDestroyed = true
    override var isRewarded = true

    companion object {
        val BITMAP_RES_LIST: List<Int> = listOf(
            R.drawable.magic_sprite_1,
            R.drawable.magic_sprite_2,
            R.drawable.magic_sprite_3,
            R.drawable.magic_sprite_4,
            R.drawable.magic_sprite_5
        )
    }

    val ANIMATION_RATE = 2  // Needed to slow down the animation cycle
    var curAnimationFrame = 1
    var curAnimationWait = 0

    // Move through the animation map
    override fun updateState() : List<HealthGainObject> {
        if (curAnimationFrame >= BITMAP_RES_LIST.size) return emptyList()

        if (curAnimationWait >= ANIMATION_RATE){
            curAnimationWait = 0

            // Advance through animation frames
            bitmapResId = BITMAP_RES_LIST[curAnimationFrame]
            bitmap = null // Reset cache it so it gets redrawn
            curAnimationFrame++
        }

        curAnimationWait++

        return listOf(this)
    }
}