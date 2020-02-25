package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.PendingIntent.getActivity
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.activities.GameActivity
import com.phatphoophoo.pdtran.herotyper.services.EnemyService
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.objects.Player
import com.phatphoophoo.pdtran.herotyper.services.BulletService
import com.phatphoophoo.pdtran.herotyper.views.GameScreenView


class GameScreenPresenter(
    val gameActivity: GameActivity,
    val gameScreenView: GameScreenView,
    val customKeyboardPresenter: CustomKeyboardPresenter,
    val windowSize: Pair<Float,Float>,
    val difficulty: GAME_DIFFICULTY
) {
    companion object {
        // Const values
        const val REFRESH_RATE : Long = 50 // In MS
    }

    var lastXPos: Float = windowSize.first/2
    var gameModel : GameScreenModel = GameScreenModel()
    val enemyService: EnemyService = EnemyService(difficulty, windowSize)
    val bulletService: BulletService = BulletService()

    val gameHandler : Handler
    val gameLooper : Runnable

    init {
        gameHandler = Handler(Looper.getMainLooper())
        gameModel.player = Player(Pair(lastXPos, windowSize.second - 200))


        gameLooper = object : Runnable {
            override fun run() {
                gameLoop()
            }
        }
        gameHandler.post(gameLooper)

        gameScreenView.setOnTouchListener { view: View, motionEvent: MotionEvent ->
            // Update the position within screen constraints
            lastXPos = Math.max(Math.min(motionEvent.x, windowSize.first - 250), 50f)
            true
        }

        // pause button event listener
        val id = gameActivity.resources.getIdentifier("pause", "id", PACKAGE_NAME)
        val btn = gameActivity.findViewById(id) as Button
        btn.setOnClickListener{
            gameHandler.removeCallbacks(gameLooper)
            gameActivity.showPauseFragment()
        }

    }

    fun gameLoop() {
        // Update the state of the game objects
        gameModel.player!!.position = Pair(lastXPos, gameModel.player!!.position.second)
        gameModel.enemies = enemyService.updateEnemies(gameModel.enemies)

        // Check for completed words to fire bullets
        if(customKeyboardPresenter.hasWordCompleted()) {
            var bulletPos = Pair(gameModel.player!!.position.first + 50,
                gameModel.player!!.position.second)

            gameModel.bullets = bulletService.updateBullets(gameModel.bullets, bulletPos)
        } else {
            gameModel.bullets = bulletService.updateBullets(gameModel.bullets)
        }

        // Check for barrier collisions
        val livesLost = enemyService.popHitStack()
        val livesLeft = gameModel.lives - livesLost

        gameModel.lives = livesLeft

        // Check for bullet-enemy collisions
        handleEnemyHit(gameModel)

        // Update the view
        gameScreenView.setModel(gameModel)

        if (livesLeft <= 0){
            endGame()
        }
        else {
            gameHandler.postDelayed(gameLooper, REFRESH_RATE)
        }
    }

    fun handleEnemyHit(gameModel: GameScreenModel) {
        val newEnemyList = gameModel.enemies.toMutableList()

        val newBulletList = gameModel.bullets.filter { bullet ->
            var collided = false
            var curIndex = 0

            for(enemy in newEnemyList) {
                collided =
                    // X collision
                    (bullet.position.first <= enemy.position.first + enemy.width &&
                            bullet.position.first + bullet.width >= enemy.position.first) &&
                    // Y collision
                    (bullet.position.second <= enemy.position.second + enemy.height &&
                            bullet.position.second >= enemy.position.second)

                if (collided) {
                    gameModel.score += enemy.scoreValue
                    newEnemyList.removeAt(curIndex)

                    break
                }
                curIndex++
            }

            !collided
        }

        gameModel.bullets = newBulletList
        gameModel.enemies = newEnemyList
    }

    fun resumeGame(){
        gameHandler.postDelayed(gameLooper, REFRESH_RATE)
    }

    fun endGame() {
        // Stop the game loop from posting so we pause
        gameHandler.removeCallbacks(gameLooper)

        gameActivity.showGameOverFragment()
    }

    // Call when the game ends
    fun dispose() {

    }
}