package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.graphics.LightingColorFilter
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.BUTTONS
import java.util.*

class KeyboardStatsView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

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

    fun renderKeyboard(
        layout: Map<Int, String>,
        onKeyPress: (b: Button) -> Unit,
        custom: Boolean? = false
    ) {
        for (bid in BUTTONS.values()) {
            val btn = findViewById<Button>(bid.id)
            btn.visibility = View.VISIBLE
            btn.text = layout[btn.id]
            if (btn.text.isBlank() && !custom!!) {
                btn.visibility = View.GONE
                continue
            }
            btn.setOnClickListener {
                onKeyPress(btn)
            }
        }
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
        for(button in BUTTONS.values()) {
            val btn = findViewById<Button>(button.id)
            val key = btn.text.toString().toLowerCase()
            val keyColor = keyColors[key]

            if (keyColor != null) {
                btn.background.colorFilter = LightingColorFilter(keyColor.value, 0)
            }
        }
    }

}