package com.phatphoophoo.pdtran.herotyper.models

import com.phatphoophoo.pdtran.herotyper.objects.Bullet
import com.phatphoophoo.pdtran.herotyper.objects.Enemy
import com.phatphoophoo.pdtran.herotyper.objects.Player

data class GameScreenModel(val difficulty: Int = 0) {
    // Game properties
    var score : Int = 0
    var lives : Int = 3
    val timeLimit : Int = 30000 // MS
    var curTime : Int = 0 // MS

    val curGoalWord : String = ""
    val curTypedIndex : String = ""
    val curTypedState : TypedState = TypedState.DEFAULT

    // Objects
    val player : Player = Player()
    val bullets : List<Bullet> = emptyList()
    val enemies : List<Enemy> = emptyList()
}

// Default = no color, starts like this
// Success = green
// Failed = red
enum class TypedState {
    FAILED, SUCCESS, DEFAULT
}