package com.phatphoophoo.pdtran.herotyper.presenters

import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.activities.GameActivity
import com.phatphoophoo.pdtran.herotyper.consts.*
import com.phatphoophoo.pdtran.herotyper.models.GAME_DIFFICULTY
import com.phatphoophoo.pdtran.herotyper.views.KeyboardGameView

class KeyboardGamePresenter(
    private val gameActivity: GameActivity,
    private val keyboardGameView: KeyboardGameView,
    gameDifficulty: GAME_DIFFICULTY
) {
    private var curLevelWords: List<String> = emptyList()
    private var curWord = ""
    private var curDictionaryPosition = 0
    private var curLetterIndex = 0
    private var hasWordCompleted = false
    private val keysMap: MutableMap<String, Pair<Int, Int>> = generateKeysMap()
    private var kbStyles: Array<String> =
        gameActivity.resources.getStringArray(R.array.keyboard_arrays)
    private var sharedPref: SharedPreferences =
        gameActivity.getSharedPreferences(
            gameActivity.packageName + "_preferences",
            Context.MODE_PRIVATE
        )

    init {
        setup()
        curLevelWords = WORD_DICTIONARY.getValue(gameDifficulty).shuffled()
        curDictionaryPosition = 0
        setNewWord()
        keyboardGameView.renderWord(curWord, curLetterIndex)
    }

    private fun setup() {
        val curKbIdx = sharedPref.getInt(gameActivity.getString(R.string.keyboard_style_key), 0)
        when (kbStyles[curKbIdx]) {
            gameActivity.getString(R.string.keyboard_style_qwerty) -> {
                keyboardGameView.renderKeyboard(qwerty, ::onKeyPress)
            }
            gameActivity.getString(R.string.keyboard_style_colemak) -> {
                keyboardGameView.renderKeyboard(colemak, ::onKeyPress)
            }
            gameActivity.getString(R.string.keyboard_style_dvorak) -> {
                keyboardGameView.renderKeyboard(dvorak, ::onKeyPress)
            }
            gameActivity.getString(R.string.keyboard_style_custom) -> {
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
            gameActivity.soundService.playSound(R.raw.button_confirm)
            keysMap.put(curLetter, Pair(hitMiss!!.first + 1, hitMiss.second))
            curLetterIndex += 1
            if (curLetterIndex == curWord.length) {
                setNewWord()
                hasWordCompleted = true
            }
        } else {
            gameActivity.soundService.playSound(R.raw.button_cancel)
            keysMap.put(curLetter, Pair(hitMiss!!.first, hitMiss.second + 1))
        }

        keyboardGameView.renderWord(curWord, curLetterIndex)
    }

    private fun setNewWord() {
        if (curDictionaryPosition >= curLevelWords.size) {
            curDictionaryPosition = 0
        }

        val newWord = curLevelWords[curDictionaryPosition]
        curDictionaryPosition++
        curWord = newWord
        curLetterIndex = 0
    }

    fun toggleInput(isPaused: Boolean) {
        keyboardGameView.setKeysListener(if (isPaused) null else ::onKeyPress)
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