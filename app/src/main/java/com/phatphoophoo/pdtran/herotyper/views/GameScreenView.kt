package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.TimeUtils
import android.widget.TextView
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import kotlinx.android.synthetic.main.game_screen_layout.view.*
import java.time.LocalDateTime

class GameScreenView : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        inflate(context, R.layout.game_screen_layout, this)
    }

    fun setModel(modelStream: GameScreenModel) {
        // TODO redraw the screen here
        val currentTime = LocalDateTime.now()
        textView.text =   "Current Date and Time is: $currentTime"
    }
}