package com.phatphoophoo.pdtran.herotyper.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.StatsModel

class StatsActivity : AppCompatActivity() {

//    lateinit var sp: SharedPreferences
//    lateinit var statsModel: StatsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

//        sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE)
//        model = StatsModel(sp)
//
//        //testing
//        model.currGameIndex = 1
//        model.wpm.add(1)
//
//        val map = mutableMapOf("a" to arrayListOf(1, 2))
//        model.keysMap.add(map)
//
//        model.write()
//        model.read()
    }
}