package com.phatphoophoo.pdtran.herotyper.presenters

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.activities.GameActivity
import com.phatphoophoo.pdtran.herotyper.models.*
import com.phatphoophoo.pdtran.herotyper.views.KeyboardGameView
import kotlin.contracts.contract

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

class KeyboardGamePresenter(
    private val activity: GameActivity,
    private val keyboardGameView: KeyboardGameView,
    gameDifficulty: GAME_DIFFICULTY
) {

    private var curLevelWords: List<String> = emptyList()
    private var curWord = ""
    private var curLetterIndex = 0
    private var hasWordCompleted = false
    private val keysMap: MutableMap<String, Pair<Int, Int>> = generateKeysMap()
    private var kbStyles: Array<String> = activity.resources.getStringArray(R.array.keyboard_arrays)
    private var sharedPref: SharedPreferences =
        activity.getSharedPreferences(
            activity.packageName + "_preferences",
            Context.MODE_PRIVATE
        )

    init {
        setup()
        curLevelWords = dictionary.getValue(gameDifficulty)
        setNewWord()
        keyboardGameView.renderWord(curWord, curLetterIndex)
    }

    private fun setup() {
        val curKbIdx = sharedPref.getInt(activity.getString(R.string.keyboard_style_key), 0)
        when (kbStyles[curKbIdx]) {
            activity.getString(R.string.keyboard_style_qwerty) -> {
                keyboardGameView.renderKeyboard(qwerty, ::onKeyPress)
            }
            activity.getString(R.string.keyboard_style_colemak) -> {
                keyboardGameView.renderKeyboard(colemak, ::onKeyPress)
            }
            activity.getString(R.string.keyboard_style_dvorak) -> {
                keyboardGameView.renderKeyboard(dvorak, ::onKeyPress)
            }
            activity.getString(R.string.keyboard_style_custom) -> {
                keyboardGameView.renderKeyboard(fetchLayout(), ::onKeyPress, true)
            }
            else -> println("Please add new keyboard style in strings.xml")
        }
    }

    private fun fetchLayout(): MutableMap<Int, String> {
        var res: MutableMap<Int, String> = mutableMapOf()
        var savedKey: String?
        for (bid in BUTTONS.values()) {
            savedKey = sharedPref.getString(bid.id.toString(), null)
            res[bid.id] = savedKey ?: ""
        }
        return res
    }

    private fun generateKeysMap(): MutableMap<String, Pair<Int, Int>> {
        val map = mutableMapOf<String, Pair<Int, Int>>()
        for (i in 1..26) {
            //Lowercase letters
            map.put((96 + i).toChar().toString(), Pair(0, 0))
        }
        return map
    }

    private fun onKeyPress(btn: Button) {
        val btnText = btn.text.toString().toLowerCase()
        val curLetter = curWord[curLetterIndex].toString().toLowerCase()

        var hitMiss = keysMap.get(curLetter)

        if (btnText == curLetter) {
            keysMap.put(curLetter, Pair(hitMiss!!.first + 1, hitMiss.second))
            curLetterIndex += 1
            if (curLetterIndex == curWord.length) {
                setNewWord()
                hasWordCompleted = true
            }
        } else {
            keysMap.put(curLetter, Pair(hitMiss!!.first, hitMiss.second + 1))
        }

        keyboardGameView.renderWord(curWord, curLetterIndex)
    }

    private fun setNewWord() {
        var newWord = curWord
        while (newWord == curWord) {
            newWord = curLevelWords[(0 until (curLevelWords.size)).random()]
        }

        curWord = newWord
        curLetterIndex = 0
    }

    fun hasWordCompleted(): Boolean {
        return if (hasWordCompleted) {
            hasWordCompleted = false
            true
        } else {
            false
        }
    }

    fun getKeysHitMissMap(): MutableMap<String, Pair<Int, Int>> {
        return keysMap
    }

}