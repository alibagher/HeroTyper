package com.phatphoophoo.pdtran.herotyper.services

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.phatphoophoo.pdtran.herotyper.models.StatsModel


object StatsService {

    lateinit var context: Context
    lateinit var sp: SharedPreferences
    lateinit var statsModel: StatsModel

    fun initService(context: Context) {
        this.context = context
        this.sp = PreferenceManager.getDefaultSharedPreferences(context)
        this.statsModel = StatsModel(sp)

//       read the data right at the beginning. !!
        statsModel = StatsModel(sp)


        //testing
//        statsModel.currGameIndex = 1
//        statsModel.wpm.add(1)
//
//        val map = mutableMapOf("a" to arrayListOf(1, 2))
//        statsModel.keysMap.add(map)
//
//        statsModel.write()
//        statsModel.read()
    }




    fun updateWpm(n: Int) {
        statsModel.wpm.add(n)
    }

    fun updateKeysMap(m : MutableMap<String, ArrayList<Int>>) {
        statsModel.keysMap.add(m)
    }

    // write data
    fun write() {
        statsModel.write()
    }

}