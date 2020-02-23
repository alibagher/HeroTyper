package com.phatphoophoo.pdtran.herotyper.presenters

import android.os.Handler
import android.os.Looper
import com.phatphoophoo.pdtran.herotyper.factories.EnemyFactory
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.views.GameScreenView


class GameScreenPresenter(
    val gameScreenView: GameScreenView,
    val windowSize: Pair<Float,Float>,
    val difficulty: GAME_DIFFICULTY
) {
    companion object {
        // Const values
        const val REFRESH_RATE : Long = 50 // In MS
    }

    var gameModel : GameScreenModel
    val enemyFactory: EnemyFactory


    init {
        gameModel = GameScreenModel()
        enemyFactory = EnemyFactory(difficulty, windowSize)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                gameLoop()
                mainHandler.postDelayed(this, REFRESH_RATE)
            }
        })
    }


    fun gameLoop() {
        // Perform logic

        // Make changes to the model

        // Update the state of the game objects
        gameModel.enemies = enemyFactory.updateEnemies(gameModel.enemies)

        // Check for collisions
        val livesLost = enemyFactory.popHitStack()
        val livesLeft = gameModel.lives - livesLost

        if (livesLeft <= 0){
            endGame()
        }

        gameModel.lives = livesLeft

        // Update the view
        gameScreenView.setModel(gameModel)
    }

    fun endGame() {
        // Cover the screen with something?
    }

    // Call when the game ends
    fun dispose() {

    }
}