package com.phatphoophoo.pdtran.herotyper.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phatphoophoo.pdtran.herotyper.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        save_settings.setOnClickListener {
            // TODO: Validate keyboard setting
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}