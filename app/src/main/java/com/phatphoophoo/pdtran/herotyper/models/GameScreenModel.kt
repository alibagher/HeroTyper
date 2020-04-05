package com.phatphoophoo.pdtran.herotyper.models

import com.phatphoophoo.pdtran.herotyper.objects.BulletObject
import com.phatphoophoo.pdtran.herotyper.objects.EnemyObject
import com.phatphoophoo.pdtran.herotyper.objects.PlayerObject
import com.phatphoophoo.pdtran.herotyper.objects.PowerupObject

data class GameScreenModel(val difficulty: GAME_DIFFICULTY = GAME_DIFFICULTY.EASY) {
    // Game properties
    var score : Int = 0
    var lives : Int = 3
    var numMissiles: Int = 3

    // Objects
    var playerObject : PlayerObject = PlayerObject(Pair(0f,0f))
    var bullets : List<BulletObject> = emptyList()
    var powerups : List<PowerupObject> = emptyList()
    var enemies : List<EnemyObject> = emptyList()
}

enum class GAME_DIFFICULTY {
    EASY, MEDIUM, HARD
}