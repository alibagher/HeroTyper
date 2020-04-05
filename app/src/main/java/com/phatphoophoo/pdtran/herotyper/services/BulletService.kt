package com.phatphoophoo.pdtran.herotyper.services
import com.phatphoophoo.pdtran.herotyper.objects.BulletObject
import com.phatphoophoo.pdtran.herotyper.objects.BasicBullet

class BulletService {
    private val bulletVelocity = 10.5f

    fun updateBullets(bullets: List<BulletObject>) : List<BulletObject> {
        // Loop over existing bullets and update their position
        bullets.forEach{ bullet ->
            if (!bullet.isDestroyed){
                val collided = bullet.position.second <= 0
                bullet.isDestroyed = collided
            }
        }

        // Loop over existing bullets and update their position
        val movedBullets = mutableListOf<BulletObject>()
        bullets.forEach{ bullet -> movedBullets.addAll(bullet.updateState())}

        return movedBullets
    }

    fun updateBullets(bullets: List<BulletObject>, newBulletPosition: Pair<Float,Float>) : List<BulletObject> {
        val newList = updateBullets(bullets).toMutableList()

        newList.add(BasicBullet(newBulletPosition, bulletVelocity))

        return newList
    }
}