package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.BUTTONS


class KeyboardSettingsView :
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(
        context,
        attrs,
        attributeSetId
    )

    init {
        inflate(context, R.layout.custom_keyboard_keys_medium, this)
    }

    fun renderKeyboard(
        layout: Map<Int, String>,
        onKeyPress: (b: Button) -> Unit? = {_: Button -> Unit},
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

}