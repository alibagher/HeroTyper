package com.phatphoophoo.pdtran.herotyper.services

import android.content.Context
import android.content.SharedPreferences
import com.phatphoophoo.pdtran.herotyper.activities.MainMenuActivity
import com.phatphoophoo.pdtran.herotyper.models.StatsModel


object StatsService {
    lateinit var sp: SharedPreferences
    lateinit var statsModel: StatsModel

    fun initService(mainMenuActivity: MainMenuActivity): StatsService {
        this.sp = mainMenuActivity?.getPreferences(Context.MODE_PRIVATE)
        this.statsModel = StatsModel(sp)

        // read the data right at the beginning. !!
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

        statsModel.write()
    }

    fun getWpm() : ArrayList<Int> {
        return statsModel.wpm
    }

    fun getKeysMap() : ArrayList<MutableMap<String, Pair<Int, Int>>>{
        var arr = statsModel.keysMap
        var ret : ArrayList<MutableMap<String, Pair<Int, Int>>> = ArrayList()

        for (m in arr ){
            var a : MutableMap<String, Pair<Int,Int>> = mutableMapOf()
            for ((k,v) in m){
                a.put(k,Pair(v[0],v[1]))
            }
            ret.add(a)
        }
        return ret
    }

    fun cleanMemory(){
        sp!!.edit().remove("currGameIndex").commit();
        sp!!.edit().remove("wpm").commit();
        sp!!.edit().remove("keysMap").commit();
    }
}