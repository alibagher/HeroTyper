package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.phatphoophoo.pdtran.herotyper.R

class KeyboardStatsView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    val ANALYSIS_THRESHOLD_COUNT = 30

    init {
        inflate(context, R.layout.custom_keyboard_keys, this)
        val mainLayout = findViewById<LinearLayout>(R.id.custom_keyboard_keys)
        mainLayout.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorWhite))
    }

    //Place key accuracy
    fun initKeyColors(keyStats: Map<String, Pair<Int, Int>>) {
        val keyButtonIds: IntArray = intArrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
            R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22,R.id.button23,
            R.id.button24, R.id.button25, R.id.button26)

        for(keyButtonId in keyButtonIds) {
            val button = findViewById<Button>(keyButtonId)
            val key = button.text
            val hitMissPair = keyStats[key]
            val total = hitMissPair!!.first + hitMissPair!!.second
            val ratio = hitMissPair!!.first / hitMissPair!!.second


            if(total < ANALYSIS_THRESHOLD_COUNT) {
                // if the key has not been pressed enough times for analysis
                button.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorDisable))
            } else if(ratio > 2) {
                button.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorPrimary))
            } else if(ratio > 1) {
                button.setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorWarn))
            } else {
                button.setBackgroundColor(ContextCompat.getColor(this.context, R.color.redOverlay))
            }

        }
    }

}