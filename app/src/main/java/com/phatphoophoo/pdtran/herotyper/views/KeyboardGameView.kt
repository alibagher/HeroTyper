package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.consts.BUTTONS


class KeyboardGameView :
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(
        context,
        attrs,
        attributeSetId
    )

    init {
        inflate(context, R.layout.custom_keyboard_layout, this)
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

    fun setKeysListener(onKeyPress: ((b: Button) -> Unit)?) {
        for (bid in BUTTONS.values()) {
            val btn = findViewById<Button>(bid.id)
            if (onKeyPress == null) {
                btn.setOnClickListener(null)
            }
            btn.setOnClickListener {
                onKeyPress?.invoke(btn)
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