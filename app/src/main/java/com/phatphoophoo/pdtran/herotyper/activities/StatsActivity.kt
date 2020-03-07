package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.StatsModel

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val sp: SharedPreferences? = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE)
        val model: StatsModel = StatsModel(sp)

        //testing
        model.writeStats(1)
        model.readStats()
    }
}