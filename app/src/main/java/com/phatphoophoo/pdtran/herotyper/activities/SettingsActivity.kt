package com.phatphoophoo.pdtran.herotyper.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.presenters.KeyboardSettingsPresenter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    lateinit var keyboardSettingsPresenter: KeyboardSettingsPresenter
    lateinit var sharedPref: SharedPreferences
    lateinit var background_volume_seekbar: SeekBar
    lateinit var sound_volume_seekbar: SeekBar
    var curKbIdx: Int = 0
    lateinit var keyboard_spinner: Spinner
    lateinit var kbStyles: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        background_volume_seekbar = findViewById(R.id.background_volume_seekbar)
        sound_volume_seekbar = findViewById(R.id.sound_volume_seekbar)
        keyboard_spinner = findViewById(R.id.keyboard_spinner)
        keyboardSettingsPresenter = KeyboardSettingsPresenter(this, keyboard_settings_view)
        kbStyles = resources.getStringArray(R.array.keyboard_arrays)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, kbStyles
        )
        keyboard_spinner.adapter = adapter
        keyboard_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View,
                position: Int, id: Long
            ) {
                keyboardSettingsPresenter.setup(position)
                curKbIdx = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // This will not have any effect as we
                // retrieve selection/set default selection
            }
        }
        sharedPref = getDefaultSharedPreferences(this)

        // Restore previous settings
        background_volume_seekbar.progress =
            sharedPref.getInt(getString(R.string.background_volume_key), 80)
        sound_volume_seekbar.progress = sharedPref.getInt(getString(R.string.sound_volume_key), 100)
        keyboard_spinner.setSelection(sharedPref.getInt(getString(R.string.keyboard_style_key), 0))

        save_settings.setOnClickListener {
            if (kbStyles[curKbIdx] == getString(R.string.keyboard_style_custom)
                && !keyboardSettingsPresenter.saveCustomLayoutIfValid()) {
                val missingChars = keyboardSettingsPresenter.missingChars()
                val toastTxt = "Invalid keyboard layout. You're missing ${ missingChars }!"
                Toast.makeText(
                    this,
                    toastTxt,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            keyboardSettingsPresenter.saveSelection()
            with(sharedPref.edit()) {
                putInt(
                    getString(R.string.background_volume_key),
                    background_volume_seekbar.progress
                )
                putInt(getString(R.string.sound_volume_key), sound_volume_seekbar.progress)
                apply()
            }

            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

}

