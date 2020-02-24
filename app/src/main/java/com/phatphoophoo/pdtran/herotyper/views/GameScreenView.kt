package com.phatphoophoo.pdtran.herotyper.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.phatphoophoo.pdtran.herotyper.R
import com.phatphoophoo.pdtran.herotyper.models.GameScreenModel
import com.phatphoophoo.pdtran.herotyper.objects.Player

class GameScreenView : View {
    private var spaceshipBitmap: Bitmap? = null
    private var enemyBitmap: Bitmap? = null
    private var enemyPosList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var enemySzList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var enemyBmList: MutableList<Bitmap> = mutableListOf()
    private var cnv: Canvas? = null

    private var player : Player = Player(Pair(0f,0f))
//    private var playerPos : Pair<Float, Float> = Pair(0f,0f)

    private var bulletBitmap: Bitmap? = null
    private var bulletPosList: MutableList<Pair<Float, Float>> = mutableListOf()
    private var bulletBmList: MutableList<Bitmap> = mutableListOf()
    private var bulletSzList: MutableList<Pair<Float, Float>> = mutableListOf()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        var paint = Paint()
        paint.textAlign = Paint.Align.CENTER
    }
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {
        spaceshipBitmap = BitmapFactory.decodeResource(resources, R.drawable.spaceship)
        enemyBitmap = BitmapFactory.decodeResource(resources, R.drawable.meteor)
        bulletBitmap = BitmapFactory.decodeResource(resources, R.drawable.rocket)
//        this.update(c)
    }

    fun setModel(modelStream: GameScreenModel) {
        // TODO: Add logic to fetch enemyBitmap list
        var loe = modelStream.enemies.toMutableList()
        enemyPosList = loe.map { enemy -> enemy.position }.toMutableList()
        enemySzList = loe.map { enemy -> Pair(enemy.width, enemy.height) }.toMutableList()

        player = modelStream.player

        var lob = modelStream.bullets.toMutableList()
        bulletPosList = lob.map { bullet -> bullet.position }.toMutableList()
        bulletSzList = lob.map { bullet -> Pair(bullet.width, bullet.height) }.toMutableList()

       //  Log.i("palayer first pos", player.position.first.toString() + " " + player.position.second.toString())

        this.invalidate()
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
        if (c != null && cnv == null) cnv = c
        // Log.i("current canvas", cnv.toString())
        var bounds = RectF(c?.clipBounds)

        //playerPos = Pair(player?.position.first, player?.position.second)

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
            c?.drawBitmap(enemyBmList[i], posX, posY, null)
        }
        bulletPosList.forEachIndexed{i, (posX, posY) ->
            c?.drawBitmap(bulletBmList[i], posX, posY, null)
            // Log.i("bulletBitmap position:", posX.toString()+ " " + posY.toString())
        }
    }
}