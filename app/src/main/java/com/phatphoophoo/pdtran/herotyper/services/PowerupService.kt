package com.phatphoophoo.pdtran.herotyper.services
import com.phatphoophoo.pdtran.herotyper.objects.MissilePowerup
import com.phatphoophoo.pdtran.herotyper.objects.PowerupObject

class PowerupService {
    private val powerupVelocity = -9.8f

    fun updatePowerup(powerups: List<PowerupObject>, newPos: Pair<Float, Float>?) : List<PowerupObject> {
        // Loop over existing bullets and update their position
        val movedPups = mutableListOf<PowerupObject>()
        powerups.forEach{ pup -> movedPups.addAll(pup.updateState())}
        if (newPos != null) {
            val randNum = (0..100).random()
            // 30% chance missile
            if (randNum < 30) {
                movedPups.add(MissilePowerup(newPos, powerupVelocity))
            }
        }

        return movedPups
    }
}