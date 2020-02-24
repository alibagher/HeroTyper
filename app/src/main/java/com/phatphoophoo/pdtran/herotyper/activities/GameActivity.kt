package com.phatphoophoo.pdtran.herotyper.activities

import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.CustomKeyboardPresenter
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.fragments.GameMenuFragment


class GameActivity : AppCompatActivity(), GameMenuFragment.GameMenuFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Calculate the game screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val scale = resources.displayMetrics.density
        val height = (displayMetrics.heightPixels - 200*scale)
        val width = (displayMetrics.widthPixels).toFloat()

        val keyboardPresenter = CustomKeyboardPresenter(this, custom_keyboard_view)
        val gameScreenPresenter = GameScreenPresenter(this, game_screen_view, keyboardPresenter, Pair(width, height), GAME_DIFFICULTY.EASY)
    }

    fun showGameOverFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = GameMenuFragment() as Fragment
        fragmentTransaction.add(R.id.game_screen_layout, fragment)
        fragmentTransaction.commit()
    }

    // Interactions from the fragment
    override fun onRestartPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResumePressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onExitPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
