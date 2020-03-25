package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.phatphoophoo.pdtran.herotyper.R
import kotlin.random.Random

class KeyboardStatsView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    val ANALYSIS_THRESHOLD_COUNT = 30
    val keyButtonIds: IntArray = intArrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7,
        R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
        R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22,R.id.button23,
        R.id.button24, R.id.button25, R.id.button26)

    init {
        inflate(context, R.layout.custom_keyboard_stats_layout, this)

        //testing
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
                button.setBackgroundResource(R.drawable.button_shape_neutral)
            } else if(ratio > 2) {
                button.setBackgroundResource(R.drawable.button_shape_green)
            } else if(ratio > 1) {
                button.setBackgroundResource(R.drawable.button_shape_yellow)
            } else {
                button.setBackgroundResource(R.drawable.button_shape_red)
            }

        }
    }

}