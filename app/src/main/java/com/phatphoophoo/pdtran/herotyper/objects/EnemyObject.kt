package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class EnemyObject(
    override var position: Pair<Float, Float>
) : GameObject() {
    abstract val scoreValue: Int

    override fun updateState() : List<EnemyObject> {
        if (isDestroyed) {
            return onObjectDestroyed()
        }

        this.position = Pair(position.first, position.second + velocity)
        return listOf(this)
    }

    open fun onObjectDestroyed() : List<EnemyObject> {
        // Create a destroyed object with same sizing and position
        return listOf(
            DestroyedEnemy(height,width,position)
        )
    }
}

// Does nothing but animate
class DestroyedEnemy (
    override var height: Float,
    override var width: Float,
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override val velocity = 0f
    override val scoreValue = 0
    override var bitmapResId: Int = R.drawable.explosion_1
    override var isDestroyed = true

    companion object {
        val BITMAP_RES_LIST: List<Int> = listOf(
            R.drawable.explosion_1,
            R.drawable.explosion_2,
            R.drawable.explosion_3,
            R.drawable.explosion_4,
            R.drawable.explosion_5,
            R.drawable.explosion_6,
            R.drawable.explosion_7,
            R.drawable.explosion_9,
            R.drawable.explosion_10,
            R.drawable.explosion_11,
            R.drawable.explosion_12
        )
    }

    val ANIMATION_RATE = 2  // Needed to slow down the animation cycle
    var curAnimationFrame = 1
    var curAnimationWait = 0

    // Move through the animation map
    override fun updateState() : List<EnemyObject> {
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

// Moves slowly towards the bottom of the screen
class BasicEnemy (
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override val velocity = 2.8f
    override var bitmapResId: Int = R.drawable.meteor
    override val height: Float = (200 + Math.random() * 100).toFloat()
    override val width: Float = this.height
    override val scoreValue = 100
}

// Moves much faster than the traditional enemy,
// smaller in size
class FastEnemy(
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override var velocity = 2.1f
    override var bitmapResId: Int = R.drawable.meteor_fast
    override val height: Float = (75 + Math.random() * 50).toFloat()
    override val width: Float = this.height / 0.8f
    override val scoreValue = 200

    private val VELOCITY_CAP = 12f

    // Move through the animation map
    override fun updateState() : List<EnemyObject> {
        if (velocity < VELOCITY_CAP){
            velocity += 0.2f
        }

        return super.updateState()
    }
}

// Moves slower, but spawns 2 basic enemies upon death
class SplittingEnemy(
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override val velocity = 1.8f
    override var bitmapResId: Int = R.drawable.meteor_split
    override val height: Float = (400 + Math.random() * 100).toFloat()
    override val width: Float = this.height
    override val scoreValue = 100

    override fun onObjectDestroyed() : List<EnemyObject> {
        return listOf(
            BasicEnemy(Pair(position.first - width/2, position.second)),
            BasicEnemy(Pair(position.first + width/2, position.second))
        )
    }
}

// Moves side to side as well as up and down
class StrafingEnemy (
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override val velocity = 3.6f
    override var bitmapResId: Int = R.drawable.meteor_strafe
    override val height: Float = (120 + Math.random() * 80).toFloat()
    override val width: Float = this.height
    override val scoreValue = 300

    var movingLeft = true

    val HORIZONTAL_LIMIT_LEFT = 100
    val HORIZONTAL_LIMIT_RIGHT = 600

    override fun updateState() : List<EnemyObject> {
        // Check if the horizontal direction should swap
        if (position.first <= HORIZONTAL_LIMIT_LEFT){ movingLeft = false }
        if (position.first >= HORIZONTAL_LIMIT_RIGHT){ movingLeft = true }

        this.position = Pair(position.first + velocity * if (movingLeft) -1 else 1,
            position.second)

        return super.updateState()
    }
}