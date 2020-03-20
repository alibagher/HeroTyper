package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.Activity
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.views.CustomKeyboardView

const val PACKAGE_NAME = "com.phatphoophoo.pdtran.herotyper"

val dictionary: Map<GAME_DIFFICULTY, List<String>> = mapOf(
    GAME_DIFFICULTY.EASY to listOf("wheat", "some", "trail", "food", "cool", "random", "jazz", "friend", "space", "ship"),
    GAME_DIFFICULTY.MEDIUM to listOf("according", "against", "business"),
    GAME_DIFFICULTY.HARD to listOf("logorrhea", "pochemuchka", "gobbledegook", "sacrilegious")
)

class CustomKeyboardPresenter(activity: Activity, private val keyboardView: CustomKeyboardView) {

    private var curLevelWords: List<String> = emptyList()
    private var curWord = ""
    private var curLetterIndex = 0
    private val difficulty = GAME_DIFFICULTY.EASY
    private var hasWordCompleted = false

    init {
        setKeyboardEventListeners(activity)
        curLevelWords = dictionary[difficulty]!!
        setNewWord()
        keyboardView.renderWord(curWord, curLetterIndex)
    }

    private fun setKeyboardEventListeners(activity: Activity) {
        for(i in 1..26) {
            val id = activity.resources.getIdentifier("button$i", "id", PACKAGE_NAME)
            val btn = activity.findViewById(id) as Button
            btn.setOnClickListener{
                onKeyPress(btn)
            }
        }
    }

    private fun onKeyPress(btn: Button) {
        val btnText = btn.text.toString().toLowerCase()
        val curLetter = curWord[curLetterIndex].toString().toLowerCase()

        if(btnText == curLetter) {
            curLetterIndex += 1
            if(curLetterIndex == curWord.length) {
                setNewWord()
                hasWordCompleted = true
            }
        }

        keyboardView.renderWord(curWord, curLetterIndex)

    }

    private fun setNewWord() {
        var newWord = curWord
        while (newWord == curWord){
            newWord = curLevelWords[(0 until (curLevelWords.size)).random()]
        }

        curWord = newWord
        curLetterIndex = 0
    }

    fun hasWordCompleted(): Boolean {
        return if(hasWordCompleted){
            hasWordCompleted = false
            true
        } else {
            false
        }
    }
}