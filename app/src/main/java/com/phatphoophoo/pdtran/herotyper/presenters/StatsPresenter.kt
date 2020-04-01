package com.phatphoophoo.pdtran.herotyper.presenters

import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.activities.StatsActivity
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

        private val keyButtonIds: IntArray = keyboardStatsView.keyButtonIds
        private val ANALYSIS_THRESHOLD_COUNT = 30

        fun initWithFakeData(){

            //Generate fake data
            val fakeKeyStats = generateFakeData()

            //Initialize using fake data
            this.initKeyColors(fakeKeyStats)

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

        private fun initKeyColors(keyStats: Map<String, Pair<Int, Int>>) {
            val keyColors = keyStats.mapValues { (key, hitMissPair) ->
                val total = hitMissPair!!.first + hitMissPair!!.second
                val ratio = hitMissPair!!.first / hitMissPair!!.second

                if(total < ANALYSIS_THRESHOLD_COUNT) {
                    // if the key has not been pressed enough times for analysis
                    KeyboardStatsView.KeyColor.softGray
                } else if (ratio > 2) {
                    KeyboardStatsView.KeyColor.softGreen
                }else if(ratio > 1) {
                    KeyboardStatsView.KeyColor.softYellow
                } else {
                    KeyboardStatsView.KeyColor.softRed
                }
            }

            keyboardStatsView.setKeyColors(keyColors)
        }
    }
}