package com.phatphoophoo.pdtran.herotyper.activities

import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.CustomKeyboardPresenter
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.fragments.GameMenuFragment
import com.phatphoophoo.pdtran.herotyper.services.StatsService
import java.lang.Math.ceil

import java.util.concurrent.TimeUnit
import kotlin.math.ceil

class GameActivity : AppCompatActivity() {
    lateinit var keyboardPresenter: CustomKeyboardPresenter
    lateinit var gameScreenPresenter: GameScreenPresenter
    private var gameMenuFragment: Fragment? = null
    lateinit var screenSize: Pair<Float,Float>
    lateinit var gameDifficulty: GAME_DIFFICULTY

    companion object {
        const val PARAM_DIFFICULTY = "GAME_ACTIVITY_PARAM_DIFFICULTY"
    }

    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        overridePendingTransition(0, 0)

        gameDifficulty = GAME_DIFFICULTY.valueOf(intent.getStringExtra(PARAM_DIFFICULTY))

        // Calculate the game screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val scale = resources.displayMetrics.density
        val height = (displayMetrics.heightPixels - 200*scale)
        val width = (displayMetrics.widthPixels).toFloat()
        screenSize = Pair(width, height)

        initGame()
    }

    fun initGame() {
        keyboardPresenter = CustomKeyboardPresenter(this, custom_keyboard_view, gameDifficulty)
        gameScreenPresenter = GameScreenPresenter(this, game_screen_view, keyboardPresenter, screenSize, gameDifficulty)
    }

    fun showGameOverFragment() {
        if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = true)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    fun showPauseFragment() {
//        stop the timer
        gameScreenPresenter.totalTime += System.currentTimeMillis() - gameScreenPresenter.start


        //if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = false)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    // Interactions from the fragment
    // TODO Look at a way to remove activity responsibilty for this
    fun onRetryPressed(view: View) {
        // save data
        saveWpm()

        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        initGame()
    }

    fun onResumePressed(view: View) {
        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        gameScreenPresenter.resumeGame()
    }

    fun onExitPressed(view: View) {
        saveWpm()
        // write to persistant memory

        StatsService.write()
        gameOver = true
        finish()
    }

    // call this function at the end of each game (retry, exit, gameover)
    // here we assume we have the correct number of words
    fun saveWpm(){

        gameScreenPresenter.totalTime += System.currentTimeMillis() - gameScreenPresenter.start
        Log.e("in saveWPM",gameScreenPresenter.totalTime.toString())
        //val mins = TimeUnit.MILLISECONDS.toMinutes(gameScreenPresenter.totalTime)
        // add data.
        StatsService.updateWpm(((gameScreenPresenter.words*60*1000)/gameScreenPresenter.totalTime).toInt())
    }
}
