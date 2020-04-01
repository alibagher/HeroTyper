package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.graphics.LightingColorFilter
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.phatphoophoo.pdtran.herotyper.R
import java.util.*

class KeyboardStatsView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    val keyButtonIds: IntArray = intArrayOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7,
        R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15,
        R.id.button16, R.id.button17, R.id.button18, R.id.button19, R.id.button20, R.id.button21, R.id.button22,R.id.button23,
        R.id.button24, R.id.button25, R.id.button26)

    enum class KeyColor(val value: Int) {
        softGray(0xBDB1B1),
        softGreen(0x87F09C),
        softYellow(0xEDF087),
        softRed(0xF08787)
    }

    enum class KeyStatus(val value: String) {
        UNKNOWN("N/A"),
        GOOD("Good"),
        WARN("Okay"),
        BAD("Bad")
    }

    init {
        inflate(context, R.layout.custom_keyboard_stats_layout, this)
    }

    fun showDetailedKeyStats(key: String, hitMissPair: Pair<Int, Int>, keyStatus: KeyStatus) {

        val keyTextView = findViewById<TextView>(R.id.key_value)
        val statusTextView = findViewById<TextView>(R.id.key_status)
        val hitsTextView = findViewById<TextView>(R.id.key_hit)
        val missesTextView = findViewById<TextView>(R.id.key_miss)

        keyTextView.text = String.format(resources.getString(R.string.keyboard_stats_key_value),
            key.toUpperCase(Locale.getDefault()))
        statusTextView.text = String.format(resources.getString(R.string.keyboard_stats_key_status), keyStatus.value)
        hitsTextView.text = String.format(resources.getString(R.string.keyboard_stats_key_hits), hitMissPair.first)
        missesTextView.text = String.format(resources.getString(R.string.keyboard_stats_key_misses, hitMissPair.second))


    }

    fun setKeyColors(keyColors: Map<String, KeyColor>) {
        for(keyButtonId in keyButtonIds) {
            val button = findViewById<Button>(keyButtonId)
            val key = button.text.toString()
            val keyColor = keyColors[key]

            if (keyColor != null) {
                button.background.colorFilter = LightingColorFilter(keyColor.value, 0)
            }
        }
    }

}