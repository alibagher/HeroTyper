package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.Activity
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.views.KeyboardGameView

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

class KeyboardGamePresenter(activity: Activity, private val keyboardGameView: KeyboardGameView, gameDifficulty: GAME_DIFFICULTY) {

    private var curLevelWords: List<String> = emptyList()
    private var curWord = ""
    private var curLetterIndex = 0
    private var hasWordCompleted = false

    private val keysMap : MutableMap<String, Pair<Int, Int>> = generateKeysMap()

    init {
        setKeyboardEventListeners(activity)
        curLevelWords = dictionary.getValue(gameDifficulty)
        setNewWord()
        keyboardGameView.renderWord(curWord, curLetterIndex)
    }

    private fun generateKeysMap(): MutableMap<String, Pair<Int, Int>>{
        val map = mutableMapOf<String, Pair<Int, Int>>()
        for(i in 1..26) {
            //Lowercase letters
            map.put((96+i).toChar().toString() ,Pair(0,0))
        }
        return map
    }

    private fun setKeyboardEventListeners(activity: Activity) {
        for(i in 1..30) {
            val id = activity.resources.getIdentifier("button$i", "id", activity.packageName)
            val btn = activity.findViewById(id) as Button
            // Ignore blank keys
            if (btn.text.isBlank()) {
                continue
            }
            btn.setOnClickListener{
                onKeyPress(btn)
            }
        }
    }

    private fun onKeyPress(btn: Button) {
        val btnText = btn.text.toString().toLowerCase()
        val curLetter = curWord[curLetterIndex].toString().toLowerCase()

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

        keyboardGameView.renderWord(curWord, curLetterIndex)
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

    fun getKeysHitMissMap() : MutableMap<String, Pair<Int, Int>> {
        return keysMap
    }

}