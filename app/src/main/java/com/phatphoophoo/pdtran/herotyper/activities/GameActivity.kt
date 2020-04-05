package com.phatphoophoo.pdtran.herotyper.activities

import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.fragments.GameMenuFragment
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.presenters.GameScreenPresenter
import com.phatphoophoo.pdtran.herotyper.presenters.KeyboardGamePresenter
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {
    lateinit var keyboardGamePresenter: KeyboardGamePresenter
    lateinit var gameScreenPresenter: GameScreenPresenter
    private var gameMenuFragment: Fragment? = null
    lateinit var screenSize: Pair<Float,Float>
    lateinit var gameDifficulty: GAME_DIFFICULTY
    private var backgroundVolume : Int = 0
    private var soundVolume : Int = 0
    lateinit var soundPool : SoundPool
    lateinit var sharedPref: SharedPreferences
    private val soundMap: MutableMap<String, Int> = mutableMapOf()

    companion object {
        const val PARAM_DIFFICULTY = "GAME_ACTIVITY_PARAM_DIFFICULTY"
    }

    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        overridePendingTransition(0, 0)

        gameDifficulty = GAME_DIFFICULTY.valueOf(intent.getStringExtra(PARAM_DIFFICULTY))

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(20)
            .setAudioAttributes(audioAttributes)
            .build()

        soundMap["battleLoop"] = soundPool.load(this, R.raw.battle_loop, 10)
        soundMap["shotFired"] = soundPool.load(this, R.raw.shot_fired, 1)
        soundMap["baseExplosion"] = soundPool.load(this, R.raw.base_explosion, 1)
        soundMap["asteroidExplosion"] = soundPool.load(this, R.raw.asteroid_explosion, 1)
        soundMap["startLevel"] = soundPool.load(this, R.raw.start_level, 1)
        soundMap["buttonConfirm"] = soundPool.load(this, R.raw.button_confirm, 1)
        soundMap["buttonCancel"] = soundPool.load(this, R.raw.button_cancel, 1)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        backgroundVolume = sharedPref.getInt(getString(R.string.background_volume_key), 80)
        soundVolume = sharedPref.getInt(getString(R.string.sound_volume_key), 100)

        // Calculate the game screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val scale = resources.displayMetrics.density
        val height = (displayMetrics.heightPixels - 200*scale)
        val width = (displayMetrics.widthPixels).toFloat()
        screenSize = Pair(width, height)

        playSound("battleLoop")

        initGame()
    }

    fun initGame() {
        keyboardGamePresenter = KeyboardGamePresenter(this, keyboard_game_view, gameDifficulty)
        gameScreenPresenter = GameScreenPresenter(this, game_screen_view, keyboardGamePresenter, screenSize, gameDifficulty)
    }

    fun showGameOverFragment() {
        if (gameOver) return
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = true)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    fun showPauseFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        gameMenuFragment = GameMenuFragment.newInstance(isGameOver = false)
        fragmentTransaction.add(R.id.game_screen_layout, gameMenuFragment!!)
        fragmentTransaction.commit()
    }

    fun hidePauseFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(gameMenuFragment as GameMenuFragment)
        fragmentTransaction.commit()
    }

    // Interactions from the fragment
    // TODO Look at a way to remove activity responsibility for this
    fun onRetryPressed(view: View) {
        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }

        initGame()
    }

    fun onResumePressed(view: View) {
        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        gameScreenPresenter.resumeGame()
    }

    fun onExitPressed(view: View) {
        gameOver = true
        finish()
    }

    fun playSound(s : String){
        val soundToPlay = soundMap[s]!!
        if (s == "battleLoop"){
            Thread(Runnable {
                while (soundPool.play(soundToPlay, (backgroundVolume.toFloat()/100), (backgroundVolume.toFloat()/100), 10, -1, 1.toFloat()) == 0){
                    //do nothing
                }
            }).start()
        }else {
            Thread(Runnable {
                while (soundPool.play(soundToPlay, (soundVolume.toFloat()/100), (soundVolume.toFloat()/100), 1, 0, 1.toFloat()) == 0){
                    //do nothing
                }
            }).start()
        }
    }

    fun pauseSound() = soundPool.autoPause()
    fun resumeSound() = soundPool.autoResume()

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}
