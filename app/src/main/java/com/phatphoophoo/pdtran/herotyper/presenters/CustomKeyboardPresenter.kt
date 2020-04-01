package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.views.CustomKeyboardView

const val PACKAGE_NAME = "com.phatphoophoo.pdtran.herotyper"

val dictionary: Map<GAME_DIFFICULTY, List<String>> = mapOf(
    GAME_DIFFICULTY.EASY to listOf(
        "glow",
        "height",
        "deal",
        "waste",
        "brown",
        "leash",
        "rule",
        "home",
        "pat",
        "block",
        "tune",
        "cut",
        "spread",
        "weave",
        "deer",
        "cell",
        "leaf",
        "pluck",
        "north",
        "round"
    ),
    GAME_DIFFICULTY.MEDIUM to listOf(
        "correction",
        "exemption",
        "formation",
        "exclusive",
        "candidate",
        "difference",
        "episode",
        "organize",
        "accurate",
        "average",
        "horizon",
        "apathy",
        "particle",
        "bulletin",
        "medieval",
        "objective",
        "analyst",
        "shareholder",
        "consciousness",
        "permanent"
    ),
    GAME_DIFFICULTY.HARD to listOf(
        "intermediate",
        "decoration",
        "unanimous",
        "execution",
        "variation",
        "institution",
        "acceptable",
        "deteriorate",
        "coincidence",
        "notorious",
        "qualification",
        "electronics",
        "resignation",
        "continental",
        "entertainment",
        "initiative",
        "economist",
        "architecture",
        "demonstrator",
        "integrated"
    )
)

class CustomKeyboardPresenter(activity: Activity, private val keyboardView: CustomKeyboardView, gameDifficulty: GAME_DIFFICULTY) {

    private var curLevelWords: List<String> = emptyList()
    private var curWord = ""
    private var curLetterIndex = 0
    private var hasWordCompleted = false

    private var keysMap : MutableMap<String, Pair<Int, Int>> = mutableMapOf()

    init {
        setKeyboardEventListeners(activity)
        curLevelWords = dictionary.getValue(gameDifficulty)
        setNewWord()
        keyboardView.renderWord(curWord, curLetterIndex)
    }

    private fun setKeyboardEventListeners(activity: Activity) {
        for(i in 1..26) {
            // set map. lower case!
            Log.e("the letter: ", (96+i).toChar().toString() )
            keysMap.put((96+i).toChar().toString() ,Pair(0,0))

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

        Log.e("keysMap at currLetter", keysMap.get(curLetter).toString() + " and currLetter: " + curLetter)
        var hitMiss = keysMap.get(curLetter)

        if(btnText == curLetter) {
            keysMap.put(curLetter, Pair(hitMiss!!.first+1, hitMiss!!.second))
            curLetterIndex += 1
            if(curLetterIndex == curWord.length) {
                setNewWord()
                hasWordCompleted = true
            }
        }else{
            keysMap.put(curLetter, Pair(hitMiss!!.first, hitMiss!!.second+1))
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

    fun getkeysMap() : MutableMap<String, Pair<Int, Int>> {
        return keysMap
    }

}