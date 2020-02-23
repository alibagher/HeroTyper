package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel

class GameScreenView : View {
    private var spaceship: Bitmap? = null
    private val spaceshipX: Int = 200
    private val spaceshipY: Int = 200
    private var enemy: Bitmap? = null
    private var enemyPosList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var enemySzList: MutableList<Pair<Int, Int>> = mutableListOf()
    private var enemyBmList: MutableList<Bitmap> = mutableListOf()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        var paint = Paint()
        paint.textAlign = Paint.Align.CENTER
    }
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        spaceship = BitmapFactory.decodeResource(resources, R.drawable.spaceship)
        enemy = BitmapFactory.decodeResource(resources, R.drawable.meteor)
    }

    fun setModel(modelStream: GameScreenModel) {
        Log.e("setModel", "test")
        // TODO: Add logic to fetch enemy list
        enemyPosList = mutableListOf()
        enemySzList = mutableListOf()
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        var p = Paint()
        Log.e("onDraw", "test test test")
        p.setARGB(1, 0, 0, 0)
        p.textSize = 100F
        p.color = Color.DKGRAY


        var bounds = RectF(c?.clipBounds)
        var centerX: Float = bounds.centerX() - (spaceshipX / 2)
        var bottomY: Float = bounds.bottom - spaceshipY

        Log.e("centerX", centerX.toString())
        Log.e("bottomY", bottomY.toString())


        spaceship = Bitmap.createScaledBitmap(spaceship, spaceshipX, spaceshipY, false)
        enemyBmList = mutableListOf()
        for ((szX, szY) in enemySzList) {
            enemyBmList.add(Bitmap.createScaledBitmap(enemy, szX, szY, false))
        }

        c?.drawBitmap(spaceship, centerX, bottomY, null)
        for ((posX, posY) in enemyPosList) {
            c?.drawBitmap(enemy, posX, posY, null)
        }
    }
}