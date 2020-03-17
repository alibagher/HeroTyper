package com.phatphoophoo.pdtran.herotyper.objects

import com.phatphoophoo.pdtran.herotyper.R

abstract class EnemyObject(
    override var position: Pair<Float, Float>
) : GameObject() {
    abstract val scoreValue: Int

    override fun updateState() : List<EnemyObject> {
        if (isDestroyed) {
            return emptyList()
        }

        this.position = Pair(position.first, position.second + velocity)
        return listOf(this)
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
    override var bitmapResId: Int = R.drawable.meteor
    override var isDestroyed = true

    val BITMAP_RES_LIST: List<Int> = listOf()
    val ANIMATION_RATE = 3  // Needed to slow down the animation cycle
    var curAnimationFrame = 0
    var curAnimationWait = 0

    // Move through the animation map
    override fun updateState() : List<EnemyObject> {
        if (curAnimationFrame >= BITMAP_RES_LIST.size) return emptyList()

        if (curAnimationWait > ANIMATION_RATE){
            curAnimationWait = 0

            // Advance through animation frames
            bitmapResId = BITMAP_RES_LIST[curAnimationFrame]
            curAnimationFrame++
        }

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
    override val velocity = 5.8f
    override var bitmapResId: Int = R.drawable.meteor
    override val height: Float = (75 + Math.random() * 50).toFloat()
    override val width: Float = this.height / 0.8f
    override val scoreValue = 200
}

// Moves slower, but spawns 3 basic enemies upon death
class SplittingEnemy(
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override val velocity = 0.8f
    override var bitmapResId: Int = R.drawable.meteor
    override val height: Float = (400 + Math.random() * 100).toFloat()
    override val width: Float = this.height
    override val scoreValue = 100
}

// Moves side to side as well as up and down
class StrafingEnemy (
    override var position: Pair<Float, Float>
) : EnemyObject(position) {
    override val velocity = 2.0f
    override var bitmapResId: Int = R.drawable.meteor
    override val height: Float = (300 + Math.random() * 100).toFloat()
    override val width: Float = this.height
    override val scoreValue = 300

    var movingLeft = true

    val HORIZONTAL_LIMIT_LEFT = 200
    val HORIZONTAL_LIMIT_RIGHT = 500

    override fun updateState() : List<EnemyObject> {
        if (isDestroyed) return emptyList()
        super.updateState()

        // Check if the horizontal direction should swap
        if (position.first <= HORIZONTAL_LIMIT_LEFT){ movingLeft = false }
        if (position.first >= HORIZONTAL_LIMIT_RIGHT){ movingLeft = true }

        this.position = Pair(position.first + velocity * if (movingLeft) -1 else 1,
            position.second)

        return listOf(this)
    }
}

