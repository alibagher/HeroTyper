package com.phatphoophoo.pdtran.herotyper.presenters

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.activities.GameActivity
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.objects.PlayerObject
import com.phatphoophoo.pdtran.herotyper.services.BulletService
import com.phatphoophoo.pdtran.herotyper.services.EnemyService
import com.phatphoophoo.pdtran.herotyper.services.StatsService
import com.phatphoophoo.pdtran.herotyper.views.GameScreenView
import com.phatphoophoo.pdtran.herotyper.views.ScrollingBGView
import java.lang.Error


class GameScreenPresenter(
    private val gameActivity: GameActivity,
    private val gameScreenView: GameScreenView,
    private val keyboardGamePresenter: KeyboardGamePresenter,
    private val windowSize: Pair<Float,Float>,
    difficulty: GAME_DIFFICULTY
) {
    companion object {
        // Const values
        const val REFRESH_RATE : Long = 50 // In MS
    }

    private var lastXPos: Float = windowSize.first/2
    private var gameModel : GameScreenModel = GameScreenModel()
    private val enemyService: EnemyService = EnemyService(difficulty, windowSize)
    private val bulletService: BulletService = BulletService()
    private var words : Int = 0

    private var gameTimer: GameTimer?

    var gamePaused: Boolean = false
    set(newVal) {
        if (newVal) scrollingBg.animator.pause()
        else scrollingBg.animator.resume()
        field = newVal
    }


    private val gameHandler : Handler = Handler(Looper.getMainLooper())
    private val gameLooper : Runnable = Runnable { gameLoop() }
    private val scrollingBg : ScrollingBGView = gameActivity.findViewById(R.id.scrolling_content)

    init {
        scrollingBg.animator.start()
        gameModel.playerObject = PlayerObject(Pair(lastXPos, windowSize.second - 200))

        //Initialize game
        gameHandler.post(gameLooper)

        //Start game timer
        gameTimer = GameTimer()

        gameScreenView.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            // Update the position within screen constraints
            lastXPos = Math.max(Math.min(motionEvent.x, windowSize.first - 250), 50f)
            true
        }

        // pause button event listener
        val id = gameActivity.resources.getIdentifier("pause", "id", gameActivity.packageName)
        val btn = gameActivity.findViewById(id) as Button
        btn.setOnClickListener{
            gamePaused = !gamePaused
            if (gamePaused){
                gameTimer!!.pauseTimer()
                gameHandler.removeCallbacks(gameLooper)
                gameActivity.showPauseFragment()
            }
            else {
                gameActivity.hidePauseFragment()
                resumeGame()
            }
        }

        // set the start for measuring wpm.
        words = 0
    }

    // Where the Game tells various helper classes to update the state of the game,
    // while the Game itself handles logic not able to be handled solely by the object
    // type in question
    private fun gameLoop() {
        // Update the state of the game objects
        gameModel.playerObject.position = Pair(lastXPos, gameModel.playerObject.position.second)

        gameModel.enemies = enemyService.updateEnemies(gameModel.enemies)

        // Check for completed words to fire bullets
        if(keyboardGamePresenter.hasWordCompleted()) {
            val bulletPos = Pair(
                gameModel.playerObject.position.first + 50,
                gameModel.playerObject.position.second
            )

            gameModel.bullets = bulletService.updateBullets(gameModel.bullets, bulletPos)

            // add to the number of words typed.
            words += 1

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

    // Marks all objects as destroyed; they can-self handle this
    // in the next game loop
    private fun handleEnemyHit(gameModel: GameScreenModel) {
        val newEnemyList = gameModel.enemies.filter{ !it.isDestroyed }

        gameModel.bullets.forEach { bullet ->
            var collided: Boolean
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
                    enemy.isDestroyed = true
                    bullet.isDestroyed = true
                    break
                }
                curIndex++
            }
        }
    }

    fun resumeGame(){
        gameTimer!!.resumeTimer()
        gameHandler.postDelayed(gameLooper, REFRESH_RATE)
        gamePaused = false
    }

    fun endGame() {
        val totalTime = gameTimer!!.endTimer()
        this.gameTimer = null
        gamePaused = true

        //Get Hit/Miss data
        val keysHitMissMap = keyboardGamePresenter.getKeysHitMissMap()

        //Save stats info
        StatsService.setKeysMap(keysHitMissMap)
        StatsService.setWpm(((words*60*1000)/totalTime).toInt())
        StatsService.write()

        // Stop the game loop from posting so we pause
        gameHandler.removeCallbacks(gameLooper)
        gameActivity.showGameOverFragment()
    }

    // GameTimer is meant for single game use
    class GameTimer {
        private var currFrameStartTime: Long? = 0
        private var currTimeSoFar: Long = 0
        private var timerCompleted: Boolean = false
        private val invalidUseError = Error("Invalid use of timer")

        init {
            currFrameStartTime = System.currentTimeMillis()
        }

        fun pauseTimer() {
            if(timerCompleted){
                throw invalidUseError
            }
            currTimeSoFar += System.currentTimeMillis() - currFrameStartTime!!
            currFrameStartTime = null
        }

        fun resumeTimer() {
            if(timerCompleted){
                throw invalidUseError
            }
            currFrameStartTime = System.currentTimeMillis()
        }

        //Only call this when game ends
        fun endTimer(): Long {
            if(timerCompleted){
                throw invalidUseError
            }
            if(currFrameStartTime != null) {
                currTimeSoFar += System.currentTimeMillis() - currFrameStartTime!!
            }

            return currTimeSoFar
        }

    }
}