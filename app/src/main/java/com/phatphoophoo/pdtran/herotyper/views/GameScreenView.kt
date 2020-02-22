package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class DeliveryInformationListScreenView : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        inflate(context, R.layout.delivery_information_list_screen_layout, this)
    }

    fun setModel(modelStream: DeliveryInformationListScreenModel) {
        // TODO redraw the screen here

    }
}