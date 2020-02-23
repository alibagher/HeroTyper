package com.phatphoophoo.pdtran.herotyper.presenters

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import com.phatphoophoo.pdtran.herotyper.services.EnemyService
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.objects.Player
import com.phatphoophoo.pdtran.herotyper.services.BulletService
import com.phatphoophoo.pdtran.herotyper.views.GameScreenView


class GameScreenPresenter(
    val gameScreenView: GameScreenView,
    val customKeyboardPresenter: CustomKeyboardPresenter,
    val windowSize: Pair<Float,Float>,
    val difficulty: GAME_DIFFICULTY
) {
    companion object {
        // Const values
        const val REFRESH_RATE : Long = 50 // In MS
    }

    var lastXPos: Float = 0f
    var gameModel : GameScreenModel = GameScreenModel()
    val enemyService: EnemyService = EnemyService(difficulty, windowSize)
    val bulletService: BulletService = BulletService()

    init {
        val mainHandler = Handler(Looper.getMainLooper())
        gameModel.player = Player(Pair(windowSize.first/2, windowSize.second))

        mainHandler.post(object : Runnable {
            override fun run() {
                gameLoop()
                mainHandler.postDelayed(this, REFRESH_RATE)
            }
        })

        gameScreenView.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            lastXPos = motionEvent.x
            true
        }
    }

    fun gameLoop() {
        // Update the state of the game objects
        gameModel.player!!.position = Pair(lastXPos, gameModel.player!!.position.second)

        gameModel.enemies = enemyService.updateEnemies(gameModel.enemies)

        // TODO Check if we completed a word. If so, run the other update function
        // gameModel.bullets = bulletService.updateBullets(gameModel.bullets, player.position)
//        customKeyboardPresenter.
        gameModel.bullets = bulletService.updateBullets(gameModel.bullets)

        // Check for barrier collisions
        val livesLost = enemyService.popHitStack()
        val livesLeft = gameModel.lives - livesLost

        if (livesLeft <= 0){
            endGame()
        }

        gameModel.lives = livesLeft

        // Check for bullet-enemy collisions
        handleEnemyHit(gameModel)

        // Update the view
        gameScreenView.setModel(gameModel)
    }

    fun handleEnemyHit(gameModel: GameScreenModel) {
        val newBulletList = gameModel.bullets.toMutableList()
        val newEnemyList = gameModel.enemies.toMutableList()

        newBulletList.filter { bullet ->
            var collided = false
            var curIndex = 0

            for(enemy in newEnemyList) {
                curIndex ++
                collided =
                    // X collision
                    (bullet.position.first <= enemy.position.first + enemy.width ||
                            bullet.position.first + bullet.width >= enemy.position.first) &&
                    // Y collision
                    (bullet.position.second <= enemy.position.first + enemy.width ||
                            bullet.position.first + bullet.width >= enemy.position.first)

                if (collided) {
                    gameModel.score += enemy.scoreValue
                    newEnemyList.removeAt(curIndex)

                    break
                }
            }

            !collided
        }

        gameModel.bullets = newBulletList
        gameModel.enemies = newEnemyList
    }

    fun endGame() {
        // Cover the screen with something?
    }

    // Call when the game ends
    fun dispose() {

    }
}