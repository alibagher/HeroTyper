package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.Activity
import android.util.Log
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.views.CustomKeyboardView

const val PACKAGE_NAME = "com.phatphoophoo.pdtran.herotyper"

class CustomKeyboardPresenter(activity: Activity, keyboardView: CustomKeyboardView) {

    val curWord = ""
    val curWordIndex = 0

    init {
        for(i in 1..26) {
            val id = activity.resources.getIdentifier("button$i", "id", PACKAGE_NAME)
            val btn = activity.findViewById(id) as Button
            btn.setOnClickListener{
                keyPress(btn)
            }
        }
    }

    fun keyPress(btn: Button) {
        if(btn.text == "A") {
            Log.e("keyboard","Good!")
        } else {
            Log.e("keyboard","bad!")
        }
    }
}