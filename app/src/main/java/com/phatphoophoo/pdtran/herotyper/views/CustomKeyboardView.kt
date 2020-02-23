package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.presenters.CustomKeyboardPresenter


class CustomKeyboardView:
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)



    init {
        inflate(context, R.layout.custom_keyboard_layout, this)

//        for(i in 1..26) {
//            val id = resources.getIdentifier("button$i", "id", PACKAGE_NAME)
//            val btn = findViewById(id) as Button
//            btn.setOnClickListener{
//                presenter.keyPress(btn)
//            }
//        }

    }
}