package com.phatphoophoo.pdtran.herotyper.factories

import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.objects.Enemy

class EnemyFactory(
    private val difficulty: GAME_DIFFICULTY,
    val windowSize: Pair<Float,Float>
)
{
    companion object {
        // Const values
        const val SPAWN_RATE : Int = 100 // In number of ticks
        val VELOCITY_MAP : Map<GAME_DIFFICULTY, Float> = mapOf(
            GAME_DIFFICULTY.EASY to 0.2f,
            GAME_DIFFICULTY.MEDIUM to 0.5f,
            GAME_DIFFICULTY.HARD to 1f
        )
    }

    private var currentTick = 0
    private var hitStack = 0

    private fun randomEnemyPosition(): Pair<Float,Float> {
        return Pair((Math.random() * windowSize.first).toFloat(), 0f)
    }

    private fun addEnemy(enemies: List<Enemy>) : List<Enemy> {
         return if (currentTick > SPAWN_RATE) {
             // Add a new enemy
            currentTick = 0
            val newList = (enemies as MutableList)
            newList.add(Enemy(randomEnemyPosition(), VELOCITY_MAP[difficulty] ?: 1f))
            newList
        }
        else {
             // Do not modify if it's not time
             enemies
        }
    }

    private fun checkScreenCollision(enemies: List<Enemy>)  {
        // Loop over existing enemies and update their position
        enemies.filter{ enemy ->
            val collided = (enemy.position.second + enemy.height) >= windowSize.second
            if (collided) {
                hitStack ++
            }
            !collided
        }
    }

    private fun moveEnemies(enemies: List<Enemy>){
        // Loop over existing enemies and update their position
        enemies.forEach{ enemy ->
            enemy.position = Pair(enemy.position.first, enemy.position.second + enemy.velocity)
        }
    }

    fun updateEnemeies(enemies: List<Enemy>) : List<Enemy> {
        val newList = enemies.toMutableList()

        moveEnemies(newList)
        checkScreenCollision(newList)

        // Attempt to add new enemies
        return addEnemy(newList)
    }

    fun popHitStack() : Int {
        val temp = hitStack
        hitStack = 0
        return temp
    }
}