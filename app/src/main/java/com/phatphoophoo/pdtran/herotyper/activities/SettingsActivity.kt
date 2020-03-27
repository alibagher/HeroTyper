package com.phatphoophoo.pdtran.herotyper.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.text.Layout
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.phatphoophoo.pdtran.herotyper.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    lateinit var bg_seekbar: SeekBar
    lateinit var sound_seekbar: SeekBar
    var cur_keyboard: String = ""
    //    TODO: Change keyboard to ImageView for better UX
    lateinit var display_kb: ImageView
    lateinit var custom_kb: LinearLayout
    lateinit var spinner_kb: Spinner
    lateinit var kb_styles: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        bg_seekbar = findViewById(R.id.background_volume_seekbar)
        sound_seekbar = findViewById(R.id.sound_volume_seekbar)

        custom_kb = findViewById(R.id.keyboard_custom)
        display_kb = findViewById(R.id.keyboard_display)
        spinner_kb = findViewById(R.id.keyboard_spinner)
        kb_styles = resources.getStringArray(R.array.keyboard_arrays)
        if (spinner_kb != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, kb_styles
            )
            spinner_kb.adapter = adapter
            spinner_kb.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
                ) {

                    when (kb_styles[position]) {
                        "Default" -> {
                            display_kb.setImageResource(R.drawable.default_keyboard)
                        }
                        "Colemak" -> {
                            display_kb.setImageResource(R.drawable.colemak)
                        }
                        "Dvorak" -> {
                            display_kb.setImageResource(R.drawable.dvorak)
                        }
                        else -> println("Please add new keyboard style in strings.xml")
                    }
                    cur_keyboard = kb_styles[position]
                    if (cur_keyboard == "Custom") {
                        display_kb.visibility = View.GONE
                        custom_kb.visibility = View.VISIBLE
                    } else {
                        display_kb.visibility = View.VISIBLE
                        custom_kb.visibility = View.GONE
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented")
                }
            }
        }
        sharedPref = getDefaultSharedPreferences(this)

//      TODO: Fetch prev keyboard style from sharedpref
        bg_seekbar.progress = sharedPref.getInt(getString(R.string.background_volume_key), 0)
        sound_seekbar.progress = sharedPref.getInt(getString(R.string.sound_volume_key), 0)

        save_settings.setOnClickListener {
            // TODO:
            //  Validate keyboard setting
            //  Save settings

            with(sharedPref.edit()) {
                putInt(getString(R.string.background_volume_key), bg_seekbar.progress)
                putInt(getString(R.string.sound_volume_key), sound_seekbar.progress)
                apply()
            }

            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }

}

