package com.phatphoophoo.pdtran.herotyper.presenters

import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.views.GameScreenView

class GameScreenPresenter(
    val gameScreenView: GameScreenView,
    val windowSize: Pair<Int,Int>
) {
     var gameModel : GameScreenModel

    // TODO Add timer objects etc
    init {
        // Create stuff


        gameModel = GameScreenModel()
        // Setup a timer that runs game loop
    }

    fun gameLoop() {
        // Perform logic

        // Make changes to the model

        // The factories return values which we update  the game model with

        // Update the view
        gameScreenView.setModel(gameModel)
    }

    // Call when the game ends
    fun dispose() {

    }
}