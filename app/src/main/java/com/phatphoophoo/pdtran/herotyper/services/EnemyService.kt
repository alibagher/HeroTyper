package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.objects.Enemy

class EnemyService(
    private val difficulty: GAME_DIFFICULTY,
    val windowSize: Pair<Float,Float>
)
{
    companion object {
        // Const values
        const val SPAWN_RATE : Int = 250 // In number of ticks
        val VELOCITY_MAP : Map<GAME_DIFFICULTY, Float> = mapOf(
            GAME_DIFFICULTY.EASY to 2.2f,
            GAME_DIFFICULTY.MEDIUM to 3.8f,
            GAME_DIFFICULTY.HARD to 5f
        )
        const val SPAWN_OFFSET : Int = 200 // To make sure no enemies are offscreen
    }

    private var currentTick = 150 // Reduce the initial wait
    private var hitStack = 0

    private fun randomEnemyPosition(): Pair<Float,Float> {
        return Pair((Math.random() * (windowSize.first - SPAWN_OFFSET)).toFloat(), 0f)
    }

    fun updateEnemies(enemies: List<Enemy>) : List<Enemy> {
        currentTick++

        // Loop over existing enemies and check for collisions
        val newList = enemies.filter{ enemy ->
            val collided = (enemy.position.second + enemy.height) >= windowSize.second
            if (collided) {
                hitStack ++
            }
            !collided
        }.toMutableList()

        // Loop over existing enemies and update their position
        newList.forEach{ enemy -> enemy.updatePosition() }

        // Attempt to add new enemies
        if (currentTick > SPAWN_RATE) {
            // Add a new enemy
            currentTick = 0
            newList.add(Enemy(randomEnemyPosition(), VELOCITY_MAP[difficulty] ?: 1f))
        }

        return newList
    }

    fun popHitStack() : Int {
        val temp = hitStack
        hitStack = 0
        return temp
    }
}