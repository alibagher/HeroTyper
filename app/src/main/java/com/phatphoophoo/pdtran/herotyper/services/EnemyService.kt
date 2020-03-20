package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.objects.*

class EnemyService(
    private val difficulty: GAME_DIFFICULTY,
    val windowSize: Pair<Float,Float>
)
{
    val SPAWN_STRATEGIES : Map<GAME_DIFFICULTY, () -> List<EnemyObject>> = mapOf(
        GAME_DIFFICULTY.EASY to this::addEnemyEasy,
        GAME_DIFFICULTY.MEDIUM to this::addEnemyMedium,
        GAME_DIFFICULTY.HARD to this::addEnemyHard
    )

    val SPAWN_OFFSET : Int = 200 // To make sure no enemies are offscreen

    private var currentTick = 150 // Reduce the initial wait
    private var hitStack = 0

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
        newList.addAll(SPAWN_STRATEGIES.getValue(difficulty).invoke())

        return newList
    }

     // --- Strategies for adding enemies based off the game difficulty set ---
    private fun addEnemyEasy() : List<EnemyObject> {
         if (currentTick < 200) return emptyList()
         currentTick = 0

         // Random value between 0 and 100
         val rand = (Math.random() * 100).toInt()

         val newEnemy = when (rand){
             in 0..80 -> { BasicEnemy(randomEnemyPosition()) }
             in 81..90 -> { FastEnemy(randomEnemyPosition())}
             else -> SplittingEnemy(randomEnemyPosition())
         }

         return listOf(newEnemy)
    }

    private fun addEnemyMedium() : List<EnemyObject> {
        if (currentTick < 180) return emptyList()
        currentTick = 0

        // Random value between 0 and 100
        val rand = (Math.random() * 100).toInt()

        val newEnemy = when (rand){
            in 0..40 -> { BasicEnemy(randomEnemyPosition()) }
            in 41..70 -> { FastEnemy(randomEnemyPosition())}
            in 71..81 -> { SplittingEnemy(randomEnemyPosition())}
            else -> StrafingEnemy(randomEnemyPosition())
        }

        return listOf(newEnemy)
    }

    private fun addEnemyHard() : List<EnemyObject> {
        if (currentTick < 160) return emptyList()
        currentTick = 0

        // Random value between 0 and 100
        val rand = (Math.random() * 100).toInt()

        val newEnemy = when (rand){
            in 0..10 -> { BasicEnemy(randomEnemyPosition()) }
            in 11..40 -> { FastEnemy(randomEnemyPosition())}
            in 41..70 -> { SplittingEnemy(randomEnemyPosition())}
            else -> StrafingEnemy(randomEnemyPosition())
        }

        return listOf(newEnemy)
    }

    // Get the number of bullets that have hit the wall,
    // so the game knows the number of lives to subtract
    fun popHitStack() : Int {
        val temp = hitStack
        hitStack = 0
        return temp
    }
}