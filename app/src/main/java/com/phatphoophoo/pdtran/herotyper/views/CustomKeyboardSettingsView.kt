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
import com.phatphoophoo.pdtran.herotyper.models.BUTTONS


class CustomKeyboardSettingsView :
    LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(
        context,
        attrs,
        attributeSetId
    )

    private var sharedPref: SharedPreferences
    private var keymaps: MutableMap<Int, String> = mutableMapOf()
    private var curKbIdx: Int = 0
    var kbStyles: Array<String>

    init {
        inflate(context, R.layout.custom_keyboard_keys_medium, this)
        sharedPref =
            context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
        kbStyles = resources.getStringArray(R.array.keyboard_arrays)
        curKbIdx = sharedPref?.getInt(context.getString(R.string.keyboard_style_key), 0)
        setupKeyboard()
    }

    private fun setupKeyboard() {
        for (bid in BUTTONS.values()) {
            val btn = findViewById<Button>(bid.id)
            btn.setOnClickListener {
                onKeyPress(btn)
            }
            val savedKey = sharedPref?.getString(bid.id.toString(), null)
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
            for (bid in BUTTONS.values()) {
                val btn = findViewById<Button>(bid.id)
                putString(bid.toString(), btn.text.toString())
                apply()
            }
        }
        return true
    }

}