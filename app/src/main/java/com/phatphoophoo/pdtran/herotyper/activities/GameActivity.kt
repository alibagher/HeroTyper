package com.phatphoophoo.pdtran.herotyper.activities

import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.KeyboardSettingsPresenter
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.fragments.GameMenuFragment
import com.phatphoophoo.pdtran.herotyper.presenters.KeyboardGamePresenter

class GameActivity : AppCompatActivity() {
    lateinit var keyboardGamePresenter: KeyboardGamePresenter
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
        keyboardGamePresenter = KeyboardGamePresenter(this, keyboard_game_view, gameDifficulty)
        gameScreenPresenter = GameScreenPresenter(this, game_screen_view, keyboardGamePresenter, screenSize, gameDifficulty)
    }

    fun showGameOverFragment() {
        if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = true)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    fun showPauseFragment() {
        //if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = false)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    fun hidePauseFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(gameMenuFragment as GameMenuFragment)
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
}
