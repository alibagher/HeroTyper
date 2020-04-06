package com.phatphoophoo.pdtran.herotyper.activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.fragments.GameMenuFragment
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import com.phatphoophoo.pdtran.herotyper.presenters.KeyboardGamePresenter
import com.phatphoophoo.pdtran.herotyper.services.SoundService
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    lateinit var keyboardGamePresenter: KeyboardGamePresenter
    lateinit var gameScreenPresenter: GameScreenPresenter
    private var gameMenuFragment: Fragment? = null
    lateinit var windowSize: Pair<Float,Float>
    lateinit var gameDifficulty: GAME_DIFFICULTY

    lateinit var soundService : SoundService

    companion object {
        const val PARAM_DIFFICULTY = "GAME_ACTIVITY_PARAM_DIFFICULTY"
    }

    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        overridePendingTransition(0, 0)

        gameDifficulty = GAME_DIFFICULTY.valueOf(intent.getStringExtra(PARAM_DIFFICULTY))

        soundService = SoundService(applicationContext)

        // Calculate the game screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val scale = resources.displayMetrics.density
        val height = (displayMetrics.heightPixels - 200*scale)
        val width = (displayMetrics.widthPixels).toFloat()
        windowSize = Pair(width, height - 74)

        initGame()
    }

    fun initGame() {
        keyboardGamePresenter = KeyboardGamePresenter(this, keyboard_game_view, gameDifficulty)
        gameScreenPresenter = GameScreenPresenter(this, game_screen_view, keyboardGamePresenter, windowSize, gameDifficulty)
    }

    fun showGameOverFragment() {
        if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = true)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    fun showPauseFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = false)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()

        soundService.playSound(R.raw.button_confirm)
    }

    fun hidePauseFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(gameMenuFragment as GameMenuFragment)
        fragmentTransaction.commit()
    }

    // Interactions from the fragment
    // TODO Look at a way to remove activity responsibility for this
    fun onRetryPressed(view: View) {
        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }

        initGame()
        soundService.playSound(R.raw.button_confirm)
    }

    fun onResumePressed(view: View) {
        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        gameScreenPresenter.resumeGame()

        soundService.playSound(R.raw.button_confirm)
    }

    fun onExitPressed(view: View) {
        gameOver = true
        finish()

        soundService.playSound(R.raw.button_cancel)
    }
}
