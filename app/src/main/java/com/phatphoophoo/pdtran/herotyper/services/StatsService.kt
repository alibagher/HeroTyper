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
    }


    fun updateGameIndex(n: Int) {
        statsModel.currGameIndex = n
    }

    fun updateWpm(n: Int) {
//        statsModel.wpm.toMutableList()
        statsModel.wpm[statsModel.currGameIndex - 1] = n
    }

    fun updateKeysMap(s: String, hits: Int, misses: Int) {
        var m : MutableMap<String, Pair<Int, Int>> = statsModel.keysMap[statsModel.currGameIndex - 1]
        m[s] = Pair(hits,misses)

        statsModel.keysMap[statsModel.currGameIndex - 1] = m
    }


}