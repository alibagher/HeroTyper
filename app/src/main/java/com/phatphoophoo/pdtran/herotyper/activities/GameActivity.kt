package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import android.graphics.Point
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY


class GameActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Calculate the game screen size
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x.toFloat()
        val height = size.y.toFloat()

        val presenter = GameScreenPresenter(game_screen_view, Pair(width, height), GAME_DIFFICULTY.EASY)
    }
}
