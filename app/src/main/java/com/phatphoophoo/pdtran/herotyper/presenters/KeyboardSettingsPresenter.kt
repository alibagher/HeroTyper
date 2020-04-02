package com.phatphoophoo.pdtran.herotyper.presenters

import android.content.Context
import android.content.SharedPreferences
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.activities.SettingsActivity
import com.phatphoophoo.pdtran.herotyper.models.BUTTONS
import com.phatphoophoo.pdtran.herotyper.models.colemak
import com.phatphoophoo.pdtran.herotyper.models.dvorak
import com.phatphoophoo.pdtran.herotyper.models.qwerty
import com.phatphoophoo.pdtran.herotyper.views.KeyboardSettingsView

class KeyboardSettingsPresenter(
    private val settingsActivity: SettingsActivity,
    private val keyboardSettingsView: KeyboardSettingsView
) {
    var kbStyles: Array<String> = settingsActivity.resources.getStringArray(R.array.keyboard_arrays)
    var curKbIdx: Int = 0
    private var sharedPref: SharedPreferences =
        settingsActivity.getSharedPreferences(
            settingsActivity.packageName + "_preferences",
            Context.MODE_PRIVATE
        )

    fun setup(pos: Int) {
        when (kbStyles[pos]) {
            settingsActivity.getString(R.string.keyboard_style_qwerty) -> {
                keyboardSettingsView.renderKeyboard(qwerty)
            }
            settingsActivity.getString(R.string.keyboard_style_colemak) -> {
                keyboardSettingsView.renderKeyboard(colemak)
            }
            settingsActivity.getString(R.string.keyboard_style_dvorak) -> {
                keyboardSettingsView.renderKeyboard(dvorak)
            }
            settingsActivity.getString(R.string.keyboard_style_custom) -> {
                keyboardSettingsView.renderKeyboard(fetchLayout(), true)
            }
            else -> println("Please add new keyboard style in strings.xml")
        }
        curKbIdx = pos
    }

    private fun fetchLayout(): MutableMap<Int, String> {
        var res: MutableMap<Int, String> = mutableMapOf()
        var savedKey: String?
        for (bid in BUTTONS.values()) {
            savedKey = sharedPref?.getString(bid.id.toString(), null)
            res[bid.id] = savedKey?: ""
        }
        return res
    }

    fun isCustomKeyboardValid(): Boolean {
        return keyboardSettingsView.isValid()
            && keyboardSettingsView.saveKeyLayout(settingsActivity)
    }

    fun saveLayout() {
        with(sharedPref.edit()) {
            putInt(settingsActivity.getString(R.string.keyboard_style_key), curKbIdx)
            apply()
        }
    }

}