package com.phatphoophoo.pdtran.herotyper.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*
import android.graphics.Point
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Calculate the game screen size
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = 600f
        val height = 600f

        // TODO calculate the height properly

        val presenter = GameScreenPresenter(game_screen_view, Pair(width, height), GAME_DIFFICULTY.EASY)
    }
}
