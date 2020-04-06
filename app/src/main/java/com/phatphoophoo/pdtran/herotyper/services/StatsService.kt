package com.phatphoophoo.pdtran.herotyper.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.phatphoophoo.pdtran.herotyper.activities.MainMenuActivity
import com.phatphoophoo.pdtran.herotyper.models.StatsModel


object StatsService {
    lateinit var sp: SharedPreferences
    lateinit var statsModel: StatsModel

    fun initService(mainMenuActivity: MainMenuActivity): StatsService {
        this.sp = mainMenuActivity?.getPreferences(Context.MODE_PRIVATE)
        this.statsModel = StatsModel(sp)

        // read the data right at the beginning
        statsModel = StatsModel(sp)

        statsModel.read()
        return this
    }

    fun setWpm(n: Int) {
        statsModel.wpm.add(n)
    }

    fun setKeysMap(m : MutableMap<String, Pair<Int, Int>>) {
        var ret : MutableMap<String, ArrayList<Int>> = mutableMapOf()

        for ((k,v) in m ){
            var a : ArrayList<Int> = ArrayList()
            a.add(v.first)
            a.add(v.second)
            ret.put(k,a)
        }

        statsModel.keysMap.add(ret)
    }

    // write data
    fun write() {
        // TODO safety check
        if(statsModel.wpm.size != statsModel.keysMap.size){
            Log.e("Alert: ", "the two lists are not the same size, fix the sizes")
            return
        }
        statsModel.write()
    }

    fun getWpm() : ArrayList<Int> {
        return statsModel.wpm
    }

    // will return the map combining all of the games' hti/miss together..
    fun getKeysMap() : MutableMap<String, Pair<Int, Int>>{
        var arr = statsModel.keysMap
        var sumMap : MutableMap<String, Pair<Int, Int>> = mutableMapOf()

        //set up an empty map
        for(i in 1..26){
            sumMap[(96+i).toChar().toString()] = Pair(0,0)
        }

        for (m in arr) {
            for ((k, v) in m) {
                if (k.isEmpty()) continue
                sumMap[k] = Pair(sumMap[k]!!.first + v[0], sumMap[k]!!.second + v[1])
            }
        }
        return sumMap
    }

    fun cleanMemory(){
        sp.edit().remove("currGameIndex").apply()
        sp.edit().remove("wpm").apply()
        sp.edit().remove("keysMap").apply()
    }
}