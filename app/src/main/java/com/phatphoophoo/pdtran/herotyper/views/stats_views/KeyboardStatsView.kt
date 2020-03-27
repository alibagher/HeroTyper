package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.graphics.LightingColorFilter
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import kotlin.random.Random

class KeyboardStatsView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    private val ANALYSIS_THRESHOLD_COUNT = 30
    private val keyButtonIds: IntArray = intArrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7,
        R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
        R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22,R.id.button23,
        R.id.button24, R.id.button25, R.id.button26)

    //TODO: change later
    //Colors
    private val softGray = 0xBDB1B1
    private val softGreen = 0x87F09C
    private val softYellow = 0xEDF087
    private val softRed = 0xF08787

    init {
        inflate(context, R.layout.custom_keyboard_stats_layout, this)

        //TODO: Remove later
        //testing
        Thread.sleep(1000) //To prevent app crash for now
        initWithFakeData()
    }

    //TODO: Remove later
    private fun initWithFakeData(){

        //Generate fake data
        val keyStats =  mutableMapOf<String, Pair<Int, Int>>()
        for(keyButtonId in keyButtonIds) {
            val button = findViewById<Button>(keyButtonId)
            val key = button.text.toString()
            val hits = Random.nextInt(0, 100)
            val misses = Random.nextInt(0, 100)

            keyStats[key] = Pair(hits, misses)
        }

        //Initialize using fake data
        initKeyColors(keyStats)
    }
    //Place key accuracy
    fun initKeyColors(keyStats: Map<String, Pair<Int, Int>>) {
        for(keyButtonId in keyButtonIds) {
            val button = findViewById<Button>(keyButtonId)
            val key = button.text.toString()
            val hitMissPair = keyStats[key]
            val total = hitMissPair!!.first + hitMissPair!!.second
            val ratio = hitMissPair!!.first / hitMissPair!!.second


            if(total < ANALYSIS_THRESHOLD_COUNT) {
                // if the key has not been pressed enough times for analysis
                button.background.colorFilter = LightingColorFilter(softGray, 0)
            } else if(ratio > 2) {
                button.background.colorFilter = LightingColorFilter(softGreen, 0)
            } else if(ratio > 1) {
                button.background.colorFilter = LightingColorFilter(softYellow, 0)
            } else {
                button.background.colorFilter = LightingColorFilter(softRed, 0)
            }

        }
    }

}