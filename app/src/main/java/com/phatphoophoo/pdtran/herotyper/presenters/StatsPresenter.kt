package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.Activity
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.activities.StatsActivity
import com.phatphoophoo.pdtran.herotyper.services.StatsService
import com.phatphoophoo.pdtran.herotyper.views.stats_views.KeyboardStatsView
import kotlin.random.Random

class StatsPresenter(
    activity: StatsActivity,
    keyboardStatsView: KeyboardStatsView
) {
    private val keyboardStatsPresenter: KeyboardStatsPresenter = KeyboardStatsPresenter(activity, keyboardStatsView)

    init {
        //TODO: Load statsmodel and modify views here
        keyboardStatsPresenter.setKeyboardEventListeners(activity)
        keyboardStatsPresenter.initWithFakeData()
    }



    private class KeyboardStatsPresenter(val activity: StatsActivity, val keyboardStatsView: KeyboardStatsView) {

        private val keyButtonIds: IntArray = intArrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
            R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23,
            R.id.button24, R.id.button25, R.id.button26)

        fun initWithFakeData(){

            //Generate fake data
            val fakeKeyStats = generateFakeData()
            //Initialize using fake data
            keyboardStatsView.initKeyColors(fakeKeyStats)

            //Initialize fake details
            val fakeKeyStatus = KeyboardStatsView.KeyStatus.GOOD
            keyboardStatsView.showDetailedKeyStats("A", fakeKeyStats["A"]!!, fakeKeyStatus)

        }

        fun generateFakeData(): Map<String, Pair<Int,Int>>{
            val keyStats =  mutableMapOf<String, Pair<Int, Int>>()
            for(keyButtonId in keyButtonIds) {
                val button = activity.findViewById<Button>(keyButtonId)
                val key = button.text.toString()
                val hits = Random.nextInt(0, 100)
                val misses = Random.nextInt(0, 100)

                keyStats[key] = Pair(hits, misses)
            }
            return keyStats
        }

        fun setKeyboardEventListeners(activity: StatsActivity) {
            for(i in 1..26) {
                val id = activity.resources.getIdentifier("button$i", "id", PACKAGE_NAME)
                val btn = activity.findViewById<Button>(id)
                btn.setOnClickListener{
                    onKeyPress(btn)
                }
            }
        }

        private fun onKeyPress(btn: Button) {
            //TODO Remove
            val fakeKeyStats = generateFakeData()
            val fakeKeyStatus = KeyboardStatsView.KeyStatus.GOOD

            keyboardStatsView.showDetailedKeyStats(btn.text.toString(), fakeKeyStats[btn.text.toString()]!!, fakeKeyStatus)
        }
    }
}