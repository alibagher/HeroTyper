package com.phatphoophoo.pdtran.herotyper.services
import com.phatphoophoo.pdtran.herotyper.objects.BulletObject
import com.phatphoophoo.pdtran.herotyper.objects.BasicBullet

class BulletService {
    private val bulletVelocity = 9.2f

    fun updateBullets(bullets: List<BulletObject>) : List<BulletObject> {
        // Loop over existing bullets and update their position
        val movedBullets = bullets.filter{ Bullet ->
            val collided = Bullet.position.second <= 0

            !collided
        }.toMutableList()

        // Loop over existing bullets and update their position
        movedBullets.forEach{ bullet -> bullet.updatePosition()}

        return movedBullets
    }

    fun updateBullets(bullets: List<BulletObject>, newBulletPosition: Pair<Float,Float>) : List<BulletObject> {
        val newList = updateBullets(bullets).toMutableList()

        newList.add(BasicBullet(newBulletPosition, bulletVelocity))

        return newList
    }
}