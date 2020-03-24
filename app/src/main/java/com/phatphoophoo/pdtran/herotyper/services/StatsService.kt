package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.activities.StatsActivity
import com.phatphoophoo.pdtran.herotyper.models.StatsModel

class StatsService (
    statsActivity: StatsActivity
) {

    val statsModel : StatsModel = statsActivity.getmodel()


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