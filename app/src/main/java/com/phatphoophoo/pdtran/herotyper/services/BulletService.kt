package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.objects.Bullet

class BulletService {
    private val bulletVelocity = 9.2f

    fun updateBullets(bullets: List<Bullet>) : List<Bullet> {
        // Loop over existing bullets and update their position
        val movedBullets = bullets.filter{ Bullet ->
            val collided = Bullet.position.second <= 0

            !collided
        }.toMutableList()

        // Loop over existing bullets and update their position
        movedBullets.forEach{ bullet -> bullet.updatePosition()}

        return movedBullets
    }

    fun updateBullets(bullets: List<Bullet>, newBulletPosition: Pair<Float,Float>) : List<Bullet> {
        val newList = updateBullets(bullets).toMutableList()

        newList.add(Bullet(newBulletPosition, bulletVelocity))

        return newList
    }
}