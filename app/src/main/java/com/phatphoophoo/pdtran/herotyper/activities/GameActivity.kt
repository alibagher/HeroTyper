package com.phatphoophoo.pdtran.herotyper.activities

import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.CustomKeyboardPresenter
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.fragments.GameMenuFragment


class GameActivity : AppCompatActivity() {
    lateinit var keyboardPresenter: CustomKeyboardPresenter
    lateinit var gameScreenPresenter: GameScreenPresenter
    private var gameMenuFragment: Fragment? = null
    private var pauseMenuFragment: Fragment? = null
    lateinit var screenSize: Pair<Float,Float>

    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
        keyboardPresenter = CustomKeyboardPresenter(this, custom_keyboard_view)
        gameScreenPresenter = GameScreenPresenter(this, game_screen_view, keyboardPresenter, screenSize, GAME_DIFFICULTY.EASY)
    }

    fun showGameOverFragment() {
        if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = true)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    // TODO: show the pause fragment
    fun showPauseFragment() {
        //if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = false)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    // Interactions from the fragment
    // TODO Look at a way to remove activity responsibilty for this
    fun onRetryPressed(view: View) {
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
        gameOver = true
        finish()
    }

    //fun onPausePressed()
}
