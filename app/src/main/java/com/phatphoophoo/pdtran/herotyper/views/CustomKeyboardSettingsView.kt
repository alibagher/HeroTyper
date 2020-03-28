package com.phatphoophoo.pdtran.herotyper.views

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R


class CustomKeyboardSettingsView :
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(
        context,
        attrs,
        attributeSetId
    )

    private val keyButtonIds: IntArray = intArrayOf(
        R.id.button1,
        R.id.button2,
        R.id.button3,
        R.id.button4,
        R.id.button5,
        R.id.button6,
        R.id.button7,
        R.id.button8,
        R.id.button9,
        R.id.button10,
        R.id.button11,
        R.id.button12,
        R.id.button13,
        R.id.button14,
        R.id.button15,
        R.id.button16,
        R.id.button17,
        R.id.button18,
        R.id.button19,
        R.id.button20,
        R.id.button21,
        R.id.button22,
        R.id.button23,
        R.id.button24,
        R.id.button25,
        R.id.button26,
        R.id.button27,
        R.id.button28,
        R.id.button29,
        R.id.button30
    )
    private var sharedPref: SharedPreferences

    init {
        inflate(context, R.layout.custom_keyboard_keys_settings, this)
        sharedPref =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
        setupKeyboard()
    }

    private fun setupKeyboard() {
        for (keyButtonId in keyButtonIds) {
            val btn = findViewById<Button>(keyButtonId)
            btn.setOnClickListener {
                onKeyPress(btn)
            }
            val savedKey = sharedPref?.getString(keyButtonId.toString(), null)
            if (savedKey != null) {
                btn.text = savedKey
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
        with(sharedPref.edit()) {
            for (keyButtonId in keyButtonIds) {
                val btn = findViewById<Button>(keyButtonId)
                putString(keyButtonId.toString(), btn.text.toString())
                apply()
            }
        }
        return true
    }

}