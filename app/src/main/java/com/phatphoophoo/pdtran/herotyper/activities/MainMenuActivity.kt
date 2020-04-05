package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.services.SoundService
import com.phatphoophoo.pdtran.herotyper.services.StatsService
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

    var currentState: MENU_STATE = MENU_STATE.MAIN

    lateinit var soundService: SoundService
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        StatsService.initService(this)

        soundService = SoundService(applicationContext)
        soundService.playBackgroundMusic(R.raw.menu_loop)

        // Setup button behavior
        start_game_button.setOnClickListener {
            setMenuState(MENU_STATE.DIFFICULTY)
        }

        easy_button.setOnClickListener{
            startGame(GAME_DIFFICULTY.EASY)
        }

        medium_button.setOnClickListener{
            startGame(GAME_DIFFICULTY.MEDIUM)
        }

        hard_button.setOnClickListener{
            startGame(GAME_DIFFICULTY.HARD)
        }

        cancel_button.setOnClickListener{
            setMenuState(MENU_STATE.MAIN)
        }

        view_stats_button.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        settings_button.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        soundService.pauseSound()
    }

    override fun onResume() {
        super.onResume()
        setMenuState(MENU_STATE.MAIN)
    }

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(0, 0)
    }

    fun startGame(difficulty: GAME_DIFFICULTY){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GameActivity.PARAM_DIFFICULTY, difficulty.toString())
        startActivity(intent)
        setMenuState(MENU_STATE.LOADING)
    }

    // Swap the current view being shown to the provided state
    fun setMenuState(state: MENU_STATE){
        val previousLayout = findViewById<View>(MENU_LAYOUT_MAP.getValue(currentState))
        previousLayout.visibility = View.GONE

        currentState = state

        val currentLayout = findViewById<View>(MENU_LAYOUT_MAP.getValue(currentState))
        currentLayout.visibility = View.VISIBLE
    }
}
