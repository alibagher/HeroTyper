package com.phatphoophoo.pdtran.herotyper.services

import com.phatphoophoo.pdtran.herotyper.objects.BasicHealthGainObject
import com.phatphoophoo.pdtran.herotyper.objects.HealthGainObject
import kotlin.random.Random

class HealthGainService(
    val windowSize: Pair<Float,Float>
) {

    private val SPAWN_OFFSET : Float = 200f // To make sure no objects are offscreen

    private var currentTicker: Int = 0
    private var nextSpawnTicker: Int? = null

    private fun randomHealthGainObjectXPos(): Float {
        return Random.nextInt(SPAWN_OFFSET.toInt(), (windowSize.first - SPAWN_OFFSET).toInt()).toFloat()
    }

    //Current impl only allows one health gain object present at a time
    fun updateHealthGainObjects(healthGainObjects: List<HealthGainObject>): List<HealthGainObject> {
        currentTicker += 1

        //If an object is present, continue its lifecycle
        if(healthGainObjects.isNotEmpty()) {
            return healthGainObjects.flatMap{ healthGainObject ->
                if(!healthGainObject.isDestroyed && !healthGainObject.isRewarded) {
                    val collidedWithWall = (healthGainObject.position.second + healthGainObject.height) >= windowSize.second
                    healthGainObject.isDestroyed = collidedWithWall
                }
                healthGainObject.updateState()
            }
        }

        //TODO: Implement better spawn strategy, possibly by level
        //If no object is present, perform the logic to generate next object
        return if(nextSpawnTicker !=null && currentTicker >= nextSpawnTicker!!) {
            nextSpawnTicker = null
            listOf(BasicHealthGainObject(randomHealthGainObjectXPos()))
        } else if(nextSpawnTicker == null) {
            currentTicker = 0
            nextSpawnTicker = currentTicker + 50 + (Math.random() * 100).toInt()
            emptyList()
        } else {
            emptyList()
        }
    }
}