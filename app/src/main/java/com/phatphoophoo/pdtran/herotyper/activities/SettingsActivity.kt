package com.phatphoophoo.pdtran.herotyper.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPreferences
    lateinit var bg_seekbar: SeekBar
    lateinit var sound_seekbar: SeekBar
    var cur_keyboard: Int = 0
//    TODO: Change keyboard to ImageView for better UX
    lateinit var default_kb: LinearLayout
    lateinit var colemak_kb: LinearLayout
    lateinit var dvorak_kb: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        bg_seekbar = findViewById(R.id.background_volume_seekbar)
        sound_seekbar = findViewById(R.id.sound_volume_seekbar)
        default_kb = findViewById(R.id.default_keyboard)
        colemak_kb = findViewById(R.id.colemak)
        dvorak_kb = findViewById(R.id.dvorak)
        sharedPref = getDefaultSharedPreferences(this)

        bg_seekbar.progress = sharedPref.getInt(getString(R.string.background_volume_key), 0)
        sound_seekbar.progress = sharedPref.getInt(getString(R.string.sound_volume_key), 0)

        default_kb.setOnClickListener {
            cur_keyboard = 0
        }
        colemak_kb.setOnClickListener {
            cur_keyboard = 1
        }
        dvorak_kb.setOnClickListener {
            cur_keyboard = 2
        }


        save_settings.setOnClickListener {
            // TODO:
            //  Validate keyboard setting
            //  Save settings

            if (cur_keyboard == 0) {
                println("Default keyboard selected")
            } else if (cur_keyboard == 1) {
                println("Colemak keyboard selected")
            } else if (cur_keyboard == 2) {
                println("Dvorak keyboard selected")
            }

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