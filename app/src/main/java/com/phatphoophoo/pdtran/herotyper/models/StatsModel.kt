package com.phatphoophoo.pdtran.herotyper.models

import android.content.SharedPreferences
import android.util.Log


class StatsModel(var sp: SharedPreferences?){

    var maxWPM : Int = 0
    var avgWPM : Int = 0
    var numSesh : Int = 0

    // Visualize typing speed over time:
    //    Key: Incremental Index: Int
    //    Value: Avg WPM (for corresponding game)
    //var typeSpeed: MutableList<Pair<Int, Int>> = mutableListOf()

    //Visualize accuracy typing speed over time:
    //    Key: Incremental Index: Int
    //    Values:
    //        Hits: Int
    //        Misses: Int
    // var : MutableList<Pair<Int, Pair<Int, Int>>> = mutableListOf()

    // Map<String, Pair<Int, Int>>


    fun readStats(){
        val editor = sp!!.edit()
        val myIntValue = sp!!.getInt("your_int_key", -1)
        // TODO: Show error if return value is -1
        if (myIntValue == -1)  true

        Log.e("read data: ", myIntValue.toString())
    }

    fun writeStats(v: Int){
        val editor = sp!!.edit()
        editor.putInt("your_int_key", v)
        editor.commit()
    }

}
