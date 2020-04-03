package com.phatphoophoo.pdtran.herotyper.activities

import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
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

    lateinit var soundPool : SoundPool
    lateinit var sharedPref: SharedPreferences
    var battleLoop : Int = 0
    var shotFired : Int = 0
    var baseExplosion : Int = 0
    var asteroidExplosion : Int = 0
    var startLevel : Int = 0
    var soundVolume : Int = 0

    companion object {
        const val PARAM_DIFFICULTY = "GAME_ACTIVITY_PARAM_DIFFICULTY"
    }

    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        overridePendingTransition(0, 0)

        gameDifficulty = GAME_DIFFICULTY.valueOf(intent.getStringExtra(PARAM_DIFFICULTY))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            soundPool = SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build()
        } else {
            soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        }

        battleLoop = soundPool.load(this, R.raw.battle_loop, 1)
        shotFired = soundPool.load(this, R.raw.shot_fired, 1)
        baseExplosion = soundPool.load(this, R.raw.base_explosion, 1)
        asteroidExplosion = soundPool.load(this, R.raw.asteroid_explosion, 1)
        startLevel = soundPool.load(this, R.raw.start_level, 1)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        soundVolume = sharedPref.getInt(getString(R.string.background_volume_key), 0)



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
        soundPool.pause(battleLoop)
        //if (gameOver) return
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
    // TODO Look at a way to remove activity responsibilty for this
    fun onRetryPressed(view: View) {
//        soundPool.resume(battleLoop)
        playSound("startLevel")

        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        startLevel
        initGame()
    }

    fun onResumePressed(view: View) {
        supportFragmentManager.popBackStackImmediate()
        for (fragment in supportFragmentManager.fragments){
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        soundPool.resume(battleLoop)
        gameScreenPresenter.resumeGame()
    }

    fun onExitPressed(view: View) {
        gameOver = true
        soundPool.stop(battleLoop)
        finish()
    }

    fun playSound(s : String){
        when (s) {
            "resumeBattleLoop" -> {
                soundPool.resume(battleLoop)
            }
            "battleLoop" -> {
                Thread(Runnable {
                    while (soundPool.play(battleLoop, ((soundVolume*.85).toFloat()/100), (soundVolume.toFloat()/100), 0, -1, 1.toFloat()) == 0){
                        //do nothing
                    }
                }).start()
            }
            "shotFired" -> {
                Thread(Runnable {
                    while (soundPool.play(shotFired, ((soundVolume*.9).toFloat()/100), (soundVolume.toFloat()/100), 0, 0, 1.toFloat()) == 0){
                        //do nothing
                    }
                }).start()
            }
            "baseExplosion" -> {
                Thread(Runnable {
                    while (soundPool.play(baseExplosion, (soundVolume.toFloat()/100), (soundVolume.toFloat()/100), 0, 0, 1.toFloat()) == 0){
                        //do nothing
                    }
                }).start()
            }
            "asteroidExplosion" -> {
                Thread(Runnable {
                    while (soundPool.play(asteroidExplosion, (soundVolume.toFloat()/100), (soundVolume.toFloat()/100), 1, 0, 1.toFloat()) == 0){
                        //do nothing
                    }
                }).start()
            }
            "startLevel"->{
                Thread(Runnable {
                    while (soundPool.play(startLevel, (soundVolume.toFloat()/100), (soundVolume.toFloat()/100), 1, 0, 1.toFloat()) == 0){
                        //do nothing
                    }
                }).start()
            }
            "pause" -> soundPool.autoPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
        soundPool
    }
}
