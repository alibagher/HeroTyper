package com.phatphoophoo.pdtran.herotyper.models

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StatsModel(val sp: SharedPreferences?){

    var currGameIndex : Int = 0
    var wpm: ArrayList<Int> = ArrayList()
    var keysMap: ArrayList<MutableMap<String, ArrayList<Int>>> = ArrayList()

    fun read(){

//        get the currGameIndex
        currGameIndex = sp!!.getInt("currGameIndex", 1)

//      get the wpm arrayList
        var gson = Gson()
        var json : String? = sp.getString("wpm", "[]")
        var itemType = object : TypeToken<ArrayList<Int>>() {}.type
        wpm = gson.fromJson(json, itemType)

//        get the keysMap
        json = sp.getString("keysMap", "[]")
        itemType = object : TypeToken<ArrayList<MutableMap<String, ArrayList<Int>>>>() {}.type
        keysMap = gson.fromJson(json, itemType)

//        if (keysMap.size != 0) {
//            var a = keysMap[0]["a"]
//            Log.e(
//                "read data: ",
//                currGameIndex.toString() + " " + wpm.toString() + " " + (keysMap).toString() //+ " " + a!![0].toString()
//            )
//        }
    }

    fun write(){
        val editor = sp!!.edit()

        currGameIndex += 1

        editor.putInt("currGameIndex", currGameIndex)
        editor.commit()

        var gson : Gson = Gson()
        var json : String = gson.toJson(wpm)
        editor.putString("wpm", json)
        editor.commit()

        json = gson.toJson(keysMap)
        editor.putString("keysMap", json)
        editor.commit()
    }

}
