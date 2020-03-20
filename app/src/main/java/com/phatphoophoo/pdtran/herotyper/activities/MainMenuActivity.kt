package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import kotlinx.android.synthetic.main.activity_main_menu.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainMenuActivity : Activity() {

    enum class MENU_STATE {
        MAIN, DIFFICULTY, LOADING
    }

    val MENU_LAYOUT_MAP: Map<MENU_STATE, Int> = mapOf(
        MENU_STATE.MAIN to R.id.menu_button_layout,
        MENU_STATE.DIFFICULTY to R.id.difficulty_button_layout,
        MENU_STATE.LOADING to R.id.loading_layout
    )

    var currentState : MENU_STATE = MENU_STATE.MAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Setup button behavior
        start_game.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
        }

        view_stats_button.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }
    }

    // Swap the current view being shown to the provided state
    fun setMenuState(state: MENU_STATE){
        val previousLayout = findViewById<View>(MENU_LAYOUT_MAP.getValue(currentState))
        previousLayout.visibility = View.GONE

        currentState = state

        val currentLayout = findViewById<View>(MENU_LAYOUT_MAP.getValue(currentState))
        currentLayout.visibility = View.VISIBLE
    }


    companion object {

        /**
         * Schedules a call to hide() in [delayMillis], canceling any
         * previously scheduled calls.
         */

        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

    }
}
