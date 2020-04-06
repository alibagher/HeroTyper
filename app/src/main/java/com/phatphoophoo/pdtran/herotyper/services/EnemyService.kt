package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.objects.*
import kotlin.math.max

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

    val SPAWN_OFFSET : Float = 200f // To make sure no enemies are offscreen

    private var currentTick = 150 // Reduce the initial wait
    private var spawnRamp = 0
    private var hitStack = 0

    private fun randomEnemyXPosition(): Float {
        return (Math.random() * (windowSize.first - SPAWN_OFFSET)).toFloat()
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
         val spawnWait = max(200 - spawnRamp, 100)

         if (currentTick < spawnWait) return emptyList()
         currentTick = 0

         // Random value between 0 and 100
         val rand = (Math.random() * 100).toInt()

         val newEnemy = when (rand){
             in 0..80 -> { BasicEnemy(randomEnemyXPosition()) }
             in 81..90 -> { FastEnemy(randomEnemyXPosition())}
             else -> SplittingEnemy(randomEnemyXPosition())
         }

         spawnRamp += Math.random().toInt() // 50/50 chance to ramp
         return listOf(newEnemy)
    }

    private fun addEnemyMedium() : List<EnemyObject> {
        val spawnWait = max(160 - spawnRamp, 80)

        if (currentTick < spawnWait) return emptyList()
        currentTick = 0

        // Random value between 0 and 100
        val rand = (Math.random() * 100).toInt()

        val newEnemy = when (rand){
            in 0..40 -> { BasicEnemy(randomEnemyXPosition()) }
            in 41..70 -> { FastEnemy(randomEnemyXPosition())}
            in 71..81 -> { SplittingEnemy(randomEnemyXPosition())}
            else -> StrafingEnemy(randomEnemyXPosition())
        }

        spawnRamp++
        return listOf(newEnemy)
    }

    private fun addEnemyHard() : List<EnemyObject> {
        val spawnWait = max(140 - spawnRamp, 40)

        if (currentTick < spawnWait) return emptyList()
        currentTick = 0

        // Random value between 0 and 100
        val rand = (Math.random() * 100).toInt()

        val newEnemy = when (rand){
            in 0..10 -> { BasicEnemy(randomEnemyXPosition()) }
            in 11..40 -> { FastEnemy(randomEnemyXPosition())}
            in 41..70 -> { SplittingEnemy(randomEnemyXPosition())}
            else -> StrafingEnemy(randomEnemyXPosition())
        }

        spawnRamp += 2
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