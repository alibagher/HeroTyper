package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.objects.*

class GameScreenView : View {
    private var cnv: Canvas? = null

    private var player : Player = Player(Pair(0f,0f))
    private var enemies : List<EnemyObject> = emptyList()
    private var bullets : List<BulletObject> = emptyList()

    private var score: Int = 0
    private val scoreText = "Score: "
    private var lives: Int = 0
    private val livesText = "Lives: "
    private val textPaint = TextPaint()

    private val cachedBitmaps: Map<Int,Bitmap>

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        textPaint.textSize = 42f
        textPaint.color = Color.WHITE

        // MUST pre-initialize bitmaps here, otherwise the game will LAG when loading them
        val bitmapList = mutableListOf(
            R.drawable.rocket,
            R.drawable.spaceship,
            R.drawable.meteor,
            R.drawable.meteor_fast,
            R.drawable.meteor_strafe,
            R.drawable.meteor_split
        )

        bitmapList.addAll(0, DestroyedEnemy.BITMAP_RES_LIST)

        cachedBitmaps = bitmapList.associateBy({it}, {BitmapFactory.decodeResource(resources, it)})
    }

    fun setModel(model: GameScreenModel) {
        enemies = model.enemies.toMutableList()
        bullets = model.bullets.toMutableList()
        player = model.player
        score = model.score
        lives = model.lives

        this.invalidate()
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (c != null && cnv == null) cnv = c

        bullets.forEach { c?.drawGameObject(it) }
        enemies.forEach { c?.drawGameObject(it) }
        c?.drawGameObject(player)

        c?.drawText("$scoreText$score", 50f, 75f, textPaint)
        c?.drawText("$livesText$lives", 50f, 130f, textPaint)
    }

    fun Canvas.drawGameObject(gameObject: GameObject) {
        // Create the bitmap if it hasn't yet
        if (gameObject.bitmap == null){
            val baseBitmap = cachedBitmaps[gameObject.bitmapResId]
            val scaledBitmap = Bitmap.createScaledBitmap(baseBitmap, gameObject.width.toInt(), gameObject.height.toInt(), false)
            gameObject.bitmap = scaledBitmap
        }

        this.drawBitmap(gameObject.bitmap, gameObject.position.first, gameObject.position.second, null)
    }
}