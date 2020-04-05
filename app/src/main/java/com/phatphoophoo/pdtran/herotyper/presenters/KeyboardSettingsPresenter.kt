package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.text.InputFilter
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.consts.*
import com.phatphoophoo.pdtran.herotyper.views.KeyboardSettingsView
import java.util.*
import com.phatphoophoo.pdtran.herotyper.activities.SettingsActivity as SettingsActivity1

class KeyboardSettingsPresenter(
    private val settingsActivity: SettingsActivity1,
    private val keyboardSettingsView: KeyboardSettingsView
) {
    private var kbStyles: Array<String> =
        settingsActivity.resources.getStringArray(R.array.keyboard_arrays)
    private var curKbIdx: Int = 0
    private var btnIdLetterMap: MutableMap<Int, String> = mutableMapOf()
    private var sharedPref: SharedPreferences =
        settingsActivity.getSharedPreferences(
            settingsActivity.packageName + "_preferences",
            Context.MODE_PRIVATE
        )

    fun setup(pos: Int) {
        when (kbStyles[pos]) {
            settingsActivity.getString(R.string.keyboard_style_qwerty) -> {
                keyboardSettingsView.renderKeyboard(qwerty)
                btnIdLetterMap = qwerty as MutableMap<Int, String>
            }
            settingsActivity.getString(R.string.keyboard_style_colemak) -> {
                keyboardSettingsView.renderKeyboard(colemak)
                btnIdLetterMap = colemak as MutableMap<Int, String>
            }
            settingsActivity.getString(R.string.keyboard_style_dvorak) -> {
                keyboardSettingsView.renderKeyboard(dvorak)
                btnIdLetterMap = dvorak as MutableMap<Int, String>
            }
            settingsActivity.getString(R.string.keyboard_style_custom) -> {
                val layout = fetchSavedOrDefaultLayout()
                keyboardSettingsView.renderKeyboard(layout, ::onKeyPress, true)
                btnIdLetterMap = layout
            }
            else -> println("Please add new keyboard style in strings.xml")
        }
        curKbIdx = pos
    }

    private fun fetchSavedOrDefaultLayout(): MutableMap<Int, String> {
        // fetch saved layout or use qwerty layout on first use
        // so users don't have to set all 26 characters manually
        val res: MutableMap<Int, String> = mutableMapOf()
        var savedKey: String?
        for (bid in BUTTONS.values()) {
            savedKey = sharedPref.getString(bid.id.toString(), null)
            res[bid.id] = savedKey ?: ""
        }
        return if (res.values.all { s -> s == "" }) qwerty as MutableMap<Int, String> else res
    }

    private fun onKeyPress(btn: Button) {
        val btnText = btn.text.toString()
        val dialogBuilder = AlertDialog.Builder(settingsActivity)
        val input = EditText(settingsActivity)
        input.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        input.filters += InputFilter.LengthFilter(1)
        input.filters += InputFilter { source, _, _, _, _, _ ->
            source.toString().filterNot { it.isWhitespace() }
        }

        dialogBuilder
            .setTitle("Modify keyboard")
            .setMessage("Replace key '$btnText' with...")
            .setView(input)
            .setPositiveButton("Accept", DialogInterface.OnClickListener { dialog, _ ->
                run {
                    btn.text = input.text.toString().toUpperCase(Locale.ROOT)
                    btnIdLetterMap[btn.id] = btn.text.toString()
                    dialog.dismiss()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
            })


        val alert = dialogBuilder.create()
        alert.show()
    }

    fun saveCustomLayoutIfValid(): Boolean {
        return isValid() && saveKeyLayout()
    }

    private fun isValid(): Boolean {
        // We only check if there are 26 unique characters
        // Users can assign the same alphabet to 2 different keys!
        return btnIdLetterMap.values.toSet().minus("").size == 26
    }

    private fun saveKeyLayout(): Boolean {
        val sharedPref = settingsActivity.getSharedPreferences(
            settingsActivity.packageName + "_preferences",
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            for (bid in BUTTONS.values()) {
                val btn = settingsActivity.findViewById<Button>(bid.id)
                putString(btn.id.toString(), btn.text.toString())
                apply()
            }
            return@saveKeyLayout true
        }
    }

    fun saveSelection() {
        with(sharedPref.edit()) {
            putInt(settingsActivity.getString(R.string.keyboard_style_key), curKbIdx)
            apply()
        }
    }

    fun missingChars(): List<String> {
        val currKeyboard = btnIdLetterMap.values.toSet()
        return alphabets.minus(currKeyboard)
    }

}