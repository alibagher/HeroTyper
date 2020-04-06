package com.phatphoophoo.pdtran.herotyper.services

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.DeadObjectException
import android.os.Handler
import com.phatphoophoo.pdtran.herotyper.R

class SoundService(context: Context) {
    private var backgroundVolume : Int = 60
    private var soundVolume : Int = 100

    private val retryDelay : Long = 5 // in MS

    private var soundPool : SoundPool
    private val soundMap: MutableMap<Int, Int> = mutableMapOf()
    private val handler: Handler = Handler()

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(20)
            .setAudioAttributes(audioAttributes)
            .build()

        soundMap[R.raw.battle_loop] = soundPool.load(context, R.raw.battle_loop, 10)
        soundMap[R.raw.menu_loop] = soundPool.load(context, R.raw.menu_loop, 10)
        soundMap[R.raw.shot_fired] = soundPool.load(context, R.raw.shot_fired, 1)
        soundMap[R.raw.base_explosion] = soundPool.load(context, R.raw.base_explosion, 1)
        soundMap[R.raw.asteroid_explosion] = soundPool.load(context, R.raw.asteroid_explosion, 1)
        soundMap[R.raw.start_level] = soundPool.load(context, R.raw.start_level, 1)
        soundMap[R.raw.button_confirm] = soundPool.load(context, R.raw.button_confirm, 1)
        soundMap[R.raw.button_cancel] = soundPool.load(context, R.raw.button_cancel, 1)

        // Setup sound levels based off shared prefs
        val sharedPref = context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
        backgroundVolume = sharedPref.getInt(context.getString(R.string.background_volume_key), backgroundVolume)
        soundVolume = sharedPref.getInt(context.getString(R.string.sound_volume_key), soundVolume)
    }

    fun playBackgroundMusic(resourceId : Int){
        val soundToPlay = soundMap[resourceId]!!
        var retry = try {
            soundPool.play(soundToPlay, (backgroundVolume.toFloat()/100), (backgroundVolume.toFloat()/100), 10, -1, 1.toFloat()) == 0
        } catch(e: DeadObjectException){
            true
        }

        if (retry) handler.postDelayed({ playBackgroundMusic(resourceId) }, retryDelay)
    }

    fun playSound(resourceId : Int){
        val soundToPlay = soundMap[resourceId]!!
        var retry = try {
            soundPool.play(soundToPlay, (soundVolume.toFloat()/100), (soundVolume.toFloat()/100), 1, 0, 1.toFloat()) == 0
        } catch(e: DeadObjectException){
            true
        }

        if (retry) handler.postDelayed({ playSound(resourceId) }, retryDelay)
    }

    fun pauseSound() = soundPool.autoPause()
    fun resumeSound() = soundPool.autoResume()
    fun stopSound(resourceId: Int) = soundPool.stop(resourceId)
}
