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
        keyboardStatsPresenter.initKeyboardEventListeners()
        keyboardStatsPresenter.initWithFakeData()
    }



    private class KeyboardStatsPresenter(val activity: StatsActivity, val keyboardStatsView: KeyboardStatsView) {

        private val keyButtonIds: IntArray = keyboardStatsView.keyButtonIds
        private val ANALYSIS_THRESHOLD_COUNT = 30
        lateinit var fakeKeyStats: Map<String, Pair<Int, Int>>

        fun initKeyboardEventListeners() {
           keyboardStatsView.setKeyboardEventListeners { btn -> onKeyPress(btn) }
        }

        fun initWithFakeData(){

            //TODO remove
            //Generate fake data
            fakeKeyStats = generateFakeData()

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

        private fun onKeyPress(btn: Button) {
            //TODO Remove
            val fakeKeyStatus = getKeyStatus(fakeKeyStats[btn.text.toString()]!!)
            keyboardStatsView.showDetailedKeyStats(btn.text.toString(), fakeKeyStats[btn.text.toString()]!!, fakeKeyStatus)
        }

        private fun initKeyColors(keyStats: Map<String, Pair<Int, Int>>) {
            val keyColors = keyStats.mapValues { (key, hitMissPair) ->
                val keyStatus = getKeyStatus(hitMissPair)

                if(keyStatus == KeyboardStatsView.KeyStatus.UNKNOWN) {
                    // if the key has not been pressed enough times for analysis
                    KeyboardStatsView.KeyColor.softGray
                } else if (keyStatus == KeyboardStatsView.KeyStatus.GOOD) {
                    KeyboardStatsView.KeyColor.softGreen
                }else if(keyStatus == KeyboardStatsView.KeyStatus.WARN) {
                    KeyboardStatsView.KeyColor.softYellow
                } else {
                    KeyboardStatsView.KeyColor.softRed
                }
            }

            keyboardStatsView.setKeyColors(keyColors)
        }

        private fun getKeyStatus(keyHitMissPair: Pair<Int, Int>): KeyboardStatsView.KeyStatus {
            val total = keyHitMissPair!!.first + keyHitMissPair!!.second
            val ratio = keyHitMissPair!!.first / keyHitMissPair!!.second

            if(total < ANALYSIS_THRESHOLD_COUNT) {
                return KeyboardStatsView.KeyStatus.UNKNOWN
            } else if (ratio > 2) {
                return KeyboardStatsView.KeyStatus.GOOD
            }else if(ratio > 1) {
                return KeyboardStatsView.KeyStatus.WARN
            } else {
                return KeyboardStatsView.KeyStatus.BAD
            }
        }
    }
}