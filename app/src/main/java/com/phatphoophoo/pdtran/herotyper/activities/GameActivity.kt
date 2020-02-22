package com.phatphoophoo.pdtran.herotyper.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val presenter = GameScreenPresenter(game_screen_view)
    }
}
