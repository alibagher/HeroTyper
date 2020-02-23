package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.objects.Bullet

class BulletService {
    private val bulletVelocity = 0.5f

    private fun checkScreenCollision(bullets: List<Bullet>)  {
        // Loop over existing bullets and update their position
        bullets.filter{ Bullet ->
            val collided = Bullet.position.second <= 0

            !collided
        }
    }

    private fun moveBullets(bullets: List<Bullet>){
        // Loop over existing bullets and update their position
        bullets.forEach{ bullet -> bullet.updatePosition()}
    }

    fun updateBullets(bullets: List<Bullet>) : List<Bullet> {
        val newList = (bullets.toMutableList())

        moveBullets(newList)
        checkScreenCollision(newList)

        return newList
    }

    fun updateBullets(bullets: List<Bullet>, newBulletPosition: Pair<Float,Float>) : List<Bullet> {
        val newList = updateBullets(bullets).toMutableList()

        newList.add(Bullet(newBulletPosition, bulletVelocity))

        return newList
    }
}