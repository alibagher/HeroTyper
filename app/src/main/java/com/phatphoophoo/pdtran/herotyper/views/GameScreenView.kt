package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel

class GameScreenView : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        inflate(context, R.layout.game_screen_layout, this)
    }

    fun setModel(modelStream: GameScreenModel) {
        // TODO redraw the screen here

    }
}