package com.phatphoophoo.pdtran.herotyper.presenters

import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.activities.StatsActivity
import com.phatphoophoo.pdtran.herotyper.models.BUTTONS
import com.phatphoophoo.pdtran.herotyper.models.colemak
import com.phatphoophoo.pdtran.herotyper.models.dvorak
import com.phatphoophoo.pdtran.herotyper.models.qwerty
import com.phatphoophoo.pdtran.herotyper.services.StatsService
import com.phatphoophoo.pdtran.herotyper.views.stats_views.KeyboardStatsView
import com.phatphoophoo.pdtran.herotyper.views.stats_views.SpeedStatsView
import kotlin.random.Random

class StatsPresenter(
    activity: StatsActivity,
    keyboardStatsView: KeyboardStatsView,
    speedStatsView: SpeedStatsView
) {
    private val keyboardStatsPresenter: KeyboardStatsPresenter =
        KeyboardStatsPresenter(activity, keyboardStatsView)

    init {
        //TODO: Load statsmodel and modify views here
        keyboardStatsPresenter.initWithFakeData()

        //Initialize speed stats graph
        val wpmList = StatsService.getWpm()
        speedStatsView.createGraph(wpmList)
    }


    private class KeyboardStatsPresenter(
        val activity: StatsActivity,
        val keyboardStatsView: KeyboardStatsView
    ) {
        private val ANALYSIS_THRESHOLD_COUNT = 10
        lateinit var fakeKeyStats: Map<String, Pair<Int, Int>>
        private var kbStyles: Array<String> = activity.resources.getStringArray(R.array.keyboard_arrays)
        private var sharedPref: SharedPreferences =
            activity.getSharedPreferences(
                activity.packageName + "_preferences",
                Context.MODE_PRIVATE
            )

        init {
            setup()
        }

        fun setup() {
            val curKbIdx = sharedPref?.getInt(activity.getString(R.string.keyboard_style_key), 0)
            when (kbStyles[curKbIdx]) {
                activity.getString(R.string.keyboard_style_qwerty) -> {
                    keyboardStatsView.renderKeyboard(qwerty, ::onKeyPress)
                }
                activity.getString(R.string.keyboard_style_colemak) -> {
                    keyboardStatsView.renderKeyboard(colemak, ::onKeyPress)
                }
                activity.getString(R.string.keyboard_style_dvorak) -> {
                    keyboardStatsView.renderKeyboard(dvorak, ::onKeyPress)
                }
                activity.getString(R.string.keyboard_style_custom) -> {
                    val layout = fetchLayout()
                    keyboardStatsView.renderKeyboard(layout, ::onKeyPress, true)
                }
                else -> println("Please add new keyboard style in strings.xml")
            }
        }

        private fun fetchLayout(): MutableMap<Int, String> {
            var res: MutableMap<Int, String> = mutableMapOf()
            var savedKey: String?
            for (bid in BUTTONS.values()) {
                savedKey = sharedPref?.getString(bid.id.toString(), null)
                res[bid.id] = savedKey ?: ""
            }
            return res
        }

        fun initWithFakeData() {

            //TODO remove
            //Generate fake data
            fakeKeyStats = generateFakeData()

            //Initialize using fake data
            this.initKeyColors(fakeKeyStats)

            //Initialize fake details
            val fakeKeyStatus = KeyboardStatsView.KeyStatus.GOOD
            keyboardStatsView.showDetailedKeyStats("A", fakeKeyStats["A"]!!, fakeKeyStatus)

        }

        fun generateFakeData(): Map<String, Pair<Int, Int>> {
            val keyStats = mutableMapOf<String, Pair<Int, Int>>()
            for (btn in BUTTONS.values()) {
                val button = activity.findViewById<Button>(btn.id)
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
            keyboardStatsView.showDetailedKeyStats(
                btn.text.toString(),
                fakeKeyStats[btn.text.toString()]!!,
                fakeKeyStatus
            )
        }

        private fun initKeyColors(keyStats: Map<String, Pair<Int, Int>>) {
            val keyColors = keyStats.mapValues { (key, hitMissPair) ->
                val keyStatus = getKeyStatus(hitMissPair)

                if (keyStatus == KeyboardStatsView.KeyStatus.UNKNOWN) {
                    // if the key has not been pressed enough times for analysis
                    KeyboardStatsView.KeyColor.softGray
                } else if (keyStatus == KeyboardStatsView.KeyStatus.GOOD) {
                    KeyboardStatsView.KeyColor.softGreen
                } else if (keyStatus == KeyboardStatsView.KeyStatus.WARN) {
                    KeyboardStatsView.KeyColor.softYellow
                } else {
                    KeyboardStatsView.KeyColor.softRed
                }
            }

            keyboardStatsView.setKeyColors(keyColors)
        }

        private fun getKeyStatus(keyHitMissPair: Pair<Int, Int>): KeyboardStatsView.KeyStatus {
            val total = keyHitMissPair!!.first + keyHitMissPair!!.second
            val ratio =
                if (keyHitMissPair!!.second > 0) (keyHitMissPair!!.first / keyHitMissPair!!.second) else 0

            if (total < ANALYSIS_THRESHOLD_COUNT) {
                return KeyboardStatsView.KeyStatus.UNKNOWN
            } else if (ratio > 2) {
                return KeyboardStatsView.KeyStatus.GOOD
            } else if (ratio > 1) {
                return KeyboardStatsView.KeyStatus.WARN
            } else {
                return KeyboardStatsView.KeyStatus.BAD
            }
        }
    }
}