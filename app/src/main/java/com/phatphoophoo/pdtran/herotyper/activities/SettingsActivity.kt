package com.phatphoophoo.pdtran.herotyper.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    lateinit var background_volume_seekbar: SeekBar
    lateinit var sound_volume_seekbar: SeekBar
    var curKb: Int = 0
    lateinit var keyboard_display: ImageView
    lateinit var keyboard_spinner: Spinner
    lateinit var kbStyles: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        background_volume_seekbar = findViewById(R.id.background_volume_seekbar)
        sound_volume_seekbar = findViewById(R.id.sound_volume_seekbar)
        keyboard_display = findViewById(R.id.keyboard_display)
        keyboard_spinner = findViewById(R.id.keyboard_spinner)
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
                when (kbStyles[position]) {
                    "Default" -> {
                        keyboard_display.setImageResource(R.drawable.default_keyboard)
                    }
                    "Colemak" -> {
                        keyboard_display.setImageResource(R.drawable.colemak)
                    }
                    "Dvorak" -> {
                        keyboard_display.setImageResource(R.drawable.dvorak)
                    }
                    else -> println("Please add new keyboard style in strings.xml")
                }
                curKb = position
                if (curKb == 3) {
                    keyboard_display.visibility = View.GONE
                    keyboard_custom.visibility = View.VISIBLE
                } else {
                    keyboard_display.visibility = View.VISIBLE
                    keyboard_custom.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented")
            }
        }
        sharedPref = getDefaultSharedPreferences(this)

//        Restore previous settings
        background_volume_seekbar.progress = sharedPref.getInt(getString(R.string.background_volume_key), 0)
        sound_volume_seekbar.progress = sharedPref.getInt(getString(R.string.sound_volume_key), 0)
        keyboard_spinner.setSelection(sharedPref.getInt(getString(R.string.keyboard_style_key), 0))

        save_settings.setOnClickListener {
            if (curKb == 3 && !keyboard_custom.isValid()) {
                Toast.makeText(
                    this,
                    "Invalid keyboard layout. Please check your layout again!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            with(sharedPref.edit()) {
                putInt(getString(R.string.background_volume_key), background_volume_seekbar.progress)
                putInt(getString(R.string.sound_volume_key), sound_volume_seekbar.progress)
                putInt(getString(R.string.keyboard_style_key), curKb)
                apply()
            }

            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

}

