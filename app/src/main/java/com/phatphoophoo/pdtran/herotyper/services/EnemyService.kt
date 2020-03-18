package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.objects.BasicEnemy
import com.phatphoophoo.pdtran.herotyper.objects.EnemyObject
import com.phatphoophoo.pdtran.herotyper.objects.FastEnemy
import com.phatphoophoo.pdtran.herotyper.objects.SplittingEnemy

class EnemyService(
    private val difficulty: GAME_DIFFICULTY,
    val windowSize: Pair<Float,Float>
)
{
    // Const values
    val SPAWN_RATE_MAP : Map<GAME_DIFFICULTY, Int> = mapOf(
        GAME_DIFFICULTY.EASY to 200,
        GAME_DIFFICULTY.MEDIUM to 160,
        GAME_DIFFICULTY.HARD to 120
    )

    val SPAWN_STRATEGIES : Map<GAME_DIFFICULTY, () -> EnemyObject> = mapOf(
        GAME_DIFFICULTY.EASY to this::addEnemyEasy,
        GAME_DIFFICULTY.MEDIUM to this::addEnemyMedium,
        GAME_DIFFICULTY.HARD to this::addEnemyHard
    )

    val SPAWN_OFFSET : Int = 200 // To make sure no enemies are offscreen

    private var currentTick = 150 // Reduce the initial wait
    private var hitStack = 0
    private var spawnRate : Int = SPAWN_RATE_MAP.getValue(this.difficulty)

    private fun randomEnemyPosition(): Pair<Float,Float> {
        return Pair((Math.random() * (windowSize.first - SPAWN_OFFSET)).toFloat(), 0f)
    }

    fun updateEnemies(enemies: List<EnemyObject>) : List<EnemyObject> {
        currentTick++
        // Loop over existing enemies and check for wall collisions
        enemies.forEach{ enemy ->
            if (!enemy.isDestroyed){
                val collided = (enemy.position.second + enemy.height) >= windowSize.second
                if (collided) {
                    hitStack ++
                }
                enemy.isDestroyed = collided
            }
        }

        val newList = mutableListOf<EnemyObject>()

        // Loop over existing enemies and update their position
        enemies.forEach{ enemy -> newList.addAll(enemy.updateState()) }

        // Attempt to add new enemies
        if (currentTick > spawnRate) {
            // Add a new enemy based off the current selected strategy
            currentTick = 0
            newList.add(SPAWN_STRATEGIES.getValue(difficulty).invoke())
        }

        return newList
    }


     // --- Strategies for adding enemies based off the game difficulty set ---
    private fun addEnemyEasy() : EnemyObject {
         // Random value between 0 and 100
         val rand = (Math.random() * 100).toInt()

         return when (rand){
             in 0..80 -> { BasicEnemy(randomEnemyPosition()) }
             in 81..90 -> { FastEnemy(randomEnemyPosition())}
             else -> SplittingEnemy(randomEnemyPosition())
         }
    }

    private fun addEnemyMedium() : EnemyObject {
        return BasicEnemy(randomEnemyPosition())
    }

    private fun addEnemyHard() : EnemyObject {
        return BasicEnemy(randomEnemyPosition())
    }

    // Get the number of bullets that have hit the wall,
    // so the game knows the number of lives to subtract
    fun popHitStack() : Int {
        val temp = hitStack
        hitStack = 0
        return temp
    }
}