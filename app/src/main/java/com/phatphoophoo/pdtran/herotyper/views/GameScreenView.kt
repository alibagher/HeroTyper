package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.objects.GameObject
import com.phatphoophoo.pdtran.herotyper.objects.Player

class GameScreenView : View {
    private var spaceshipBitmap: Bitmap? = null
    private var enemyBitmap: Bitmap? = null
    private var enemyPosList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var enemySzList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var enemyBmList: MutableList<Bitmap> = mutableListOf()
    private var cnv: Canvas? = null

    private var player : Player = Player(Pair(0f,0f))

    private var bulletBitmap: Bitmap? = null
    private var bulletPosList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var bulletBmList: MutableList<Bitmap> = mutableListOf()
    private var bulletSzList: MutableList<Pair<Float, Float>> = mutableListOf()

    private var score: Int = 0
    private val scoreText = "Score: "
    private var lives: Int = 0
    private val livesText = "Lives: "
    private val textPaint = TextPaint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        spaceshipBitmap = BitmapFactory.decodeResource(resources, R.drawable.spaceship)
        enemyBitmap = BitmapFactory.decodeResource(resources, R.drawable.meteor)
        bulletBitmap = BitmapFactory.decodeResource(resources, R.drawable.rocket)

        textPaint.textSize = 42f
        textPaint.color = Color.WHITE
    }

    fun setModel(model: GameScreenModel) {
        // TODO: Add logic to fetch enemyBitmap list
        var loe = model.enemies.toMutableList()
        enemyPosList = loe.map { enemy -> enemy.position }.toMutableList()
        enemySzList = loe.map { enemy -> Pair(enemy.width, enemy.height) }.toMutableList()

        player = model.player

        var lob = model.bullets.toMutableList()
        bulletPosList = lob.map { bullet -> bullet.position }.toMutableList()
        bulletSzList = lob.map { bullet -> Pair(bullet.width, bullet.height) }.toMutableList()

        score = model.score
        lives = model.lives

        this.invalidate()
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (c != null && cnv == null) cnv = c

        spaceshipBitmap = Bitmap.createScaledBitmap(spaceshipBitmap, player.width.toInt(), player.height.toInt(), false)
        enemyBmList.clear()
        for ((szX, szY) in enemySzList) {
            enemyBmList.add(Bitmap.createScaledBitmap(enemyBitmap, szX.toInt(), szY.toInt(), false))
        }
        bulletBmList.clear()
        for ((szX, szY) in bulletSzList) {
            bulletBmList.add(Bitmap.createScaledBitmap(bulletBitmap, szX.toInt(), szY.toInt(), false))
        }

        c?.drawBitmap(spaceshipBitmap, player?.position.first, player?.position.second, null)
        enemyPosList.forEachIndexed{i, (posX, posY) ->
            c?.drawBitmap(enemyBmList[0], posX, posY, null)
        }
        bulletPosList.forEachIndexed{i, (posX, posY) ->
            c?.drawBitmap(bulletBmList[0], posX, posY, null)
        }

        c?.drawText("$scoreText$score", 50f, 75f, textPaint)
        c?.drawText("$livesText$lives", 50f, 130f, textPaint)
    }

    fun GameObject.getObjectBitmap(gameObject: GameObject): Bitmap {
        val baseBitmap = BitmapFactory.decodeResource(resources, gameObject.bitmapResId)
        return Bitmap.createScaledBitmap(baseBitmap, gameObject.width.toInt(), gameObject.height.toInt(), false)
    }
}