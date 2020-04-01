package com.phatphoophoo.pdtran.herotyper.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.presenters.StatsPresenter
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val statsPresenter = StatsPresenter(this, keyboard_stats_view, speed_stats_view)
    }
}