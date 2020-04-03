package com.phatphoophoo.pdtran.herotyper.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.services.StatsService
import kotlinx.android.synthetic.main.activity_main_menu.*
//import sun.jvm.hotspot.utilities.IntArray


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

    var soundPool : SoundPool? = null
    lateinit var sharedPref : SharedPreferences
    var menuLoop : Int = 0
    var soundKeyVolume : Int = 0
    var streamID : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        StatsService.initService(this)

        // Setup button behavior
        start_game_button.setOnClickListener {
            setMenuState(MENU_STATE.DIFFICULTY)
        }

        easy_button.setOnClickListener{
            soundPool!!.stop(streamID)
            startGame(GAME_DIFFICULTY.EASY)
        }

        medium_button.setOnClickListener{
            soundPool!!.stop(streamID)
            startGame(GAME_DIFFICULTY.MEDIUM)
        }

        hard_button.setOnClickListener{
            soundPool!!.stop(streamID)
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
            soundPool!!.release()
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadSound()
        playSound()
        setMenuState(MENU_STATE.MAIN)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool!!.release()
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

    fun playSound(){
        Thread(Runnable {
            streamID = soundPool!!.play(menuLoop, (soundKeyVolume.toFloat()/100), (soundKeyVolume.toFloat()/100), 1, -1, 1.toFloat())
            while (streamID == 0){
                streamID = soundPool!!.play(menuLoop, (soundKeyVolume.toFloat()/100), (soundKeyVolume.toFloat()/100), 1, -1, 1.toFloat())
            }
        }).start()
    }

    fun loadSound(){
        if (soundPool != null){
            soundPool!!.release()
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttributes)
            .build()

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        soundKeyVolume = sharedPref.getInt(getString(R.string.background_volume_key), 0)
        menuLoop = soundPool!!.load(this, R.raw.menu_loop, 1)
    }
}
