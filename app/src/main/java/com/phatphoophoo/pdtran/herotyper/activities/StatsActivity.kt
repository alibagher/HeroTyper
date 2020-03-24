package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.StatsModel

class StatsActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences
    lateinit var model: StatsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE)
        model = StatsModel(sp)

        //testing
        model.writeStats(1)
        model.readStats()
    }

    fun getsp(): SharedPreferences{
        return sp
    }

    fun getmodel(): StatsModel{
        return model
    }

}