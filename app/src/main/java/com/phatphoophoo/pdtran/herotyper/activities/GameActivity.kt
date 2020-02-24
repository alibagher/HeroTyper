package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.CustomKeyboardPresenter
import android.util.DisplayMetrics
import com.phatphoophoo.pdtran.herotyper.R


class GameActivity : Activity() {

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
        val gameScreenPresenter = GameScreenPresenter(game_screen_view, keyboardPresenter, Pair(width, height), GAME_DIFFICULTY.EASY)
    }
}
