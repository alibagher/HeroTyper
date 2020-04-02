package com.phatphoophoo.pdtran.herotyper.views

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
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

    fun renderKeyboard(layout: Map<Int, String>, custom: Boolean? = false) {
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

    private fun onKeyPress(btn: Button) {
        val btnText = btn.text.toString()
        val dialogBuilder = AlertDialog.Builder(context)
        var input = EditText(context)
        input.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        input.filters += InputFilter.LengthFilter(1)
        input.filters += InputFilter {
            source, _, _, _, _, _ -> source.toString().filterNot { it.isWhitespace() }
        }

        dialogBuilder
            .setTitle("Modify keyboard")
            .setMessage("Replace key '$btnText' with...")
            .setView(input)
            .setPositiveButton("Accept", DialogInterface.OnClickListener { dialog, _ ->
                run {
                    btn.text = input.text.toString().toUpperCase()
                    dialog.dismiss()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })


        val alert = dialogBuilder.create()
        alert.show()
    }

    fun isValid(): Boolean {
//        TODO: Validate if keyboard layout is valid
        return true
    }

    fun saveKeyLayout(activity: Activity): Boolean {
        val sharedPref = activity.getSharedPreferences(
            activity.packageName + "_preferences",
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            for (bid in BUTTONS.values()) {
                val btn = activity.findViewById<Button>(bid.id)
                putString(bid.toString(), btn.text.toString())
                apply()
            }
            return true
        }
        return false
    }

}