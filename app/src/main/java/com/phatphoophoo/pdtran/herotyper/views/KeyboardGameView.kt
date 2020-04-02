package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.content.SharedPreferences
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.BUTTONS
import com.phatphoophoo.pdtran.herotyper.models.colemak
import com.phatphoophoo.pdtran.herotyper.models.dvorak
import com.phatphoophoo.pdtran.herotyper.models.qwerty


class KeyboardGameView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    private var sharedPref: SharedPreferences
    private var curKbIdx: Int = 0
    private var keymaps: MutableMap<Int, String> = mutableMapOf()
    var kbStyles: Array<String>

    init {
        inflate(context, R.layout.custom_keyboard_layout, this)
        sharedPref =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)

        kbStyles = resources.getStringArray(R.array.keyboard_arrays)
        curKbIdx = sharedPref?.getInt(context.getString(R.string.keyboard_style_key), 0)
        when (kbStyles[curKbIdx]) {
            context.getString(R.string.keyboard_style_qwerty) -> {
                keymaps.putAll(qwerty)
            }
            context.getString(R.string.keyboard_style_colemak) -> {
                keymaps.putAll(colemak)
            }
            context.getString(R.string.keyboard_style_dvorak) -> {
                keymaps.putAll(dvorak)
            }
            context.getString(R.string.keyboard_style_custom) -> {
                inflate(context, R.layout.custom_keyboard_layout, this)
            }
            else -> println("Please add new keyboard style in strings.xml")
        }

        for (bid in BUTTONS.values()) {
            val btn = findViewById<Button>(bid.id)
            var savedKey: String?
            if (kbStyles[curKbIdx] == context.getString(R.string.keyboard_style_custom)) {
                savedKey = sharedPref?.getString(bid.id.toString(), null)
            } else {
                savedKey = keymaps[bid.id]
            }
            if (savedKey != null) {
                btn.text = savedKey
            }
            if (btn.text.isBlank()){
                btn.visibility = View.GONE
            }
        }
    }

    fun getColoredSpanned(text: String, color: String): String {
        val input = "<font color=$color>$text</font>"
        return input
    }

    fun renderWord(word: String, curLetterIndex: Int) {
        val prefix = word.dropLast(word.length - curLetterIndex)
        val suffix = word.drop(curLetterIndex)

        val prefixHtml = getColoredSpanned(prefix, "#72DF6E")
        val suffixHtml = getColoredSpanned(suffix, "#000000")

        val curWordTextView = findViewById(R.id.curWordTextView) as TextView
        curWordTextView.setText(Html.fromHtml("$prefixHtml$suffixHtml"))
    }
}