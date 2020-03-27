package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView


class SpeedStatsView: LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    private val data: LineChartData = LineChartData()

    init {

        inflate(context, R.layout.speed_stats_layout, this)

        val axisX = Axis()
        val axisY = Axis().setHasLines(true)
        axisX.name = "Game Number"
        axisY.name = "WPM"

        data.axisXBottom = axisX
        data.axisYLeft = axisY

        //TODO: Remove later
        createGraph(listOf(20,32,43,56, 20,32,43,56))
    }

    fun createGraph(speedStats: List<Int>) {
        val points = speedStats.mapIndexed{idx, value -> PointValue(idx.toFloat() + 1, value.toFloat())}

        val line = Line(points)
        line.color = R.color.colorPrimary
        val lines = arrayListOf(line)

        data.lines = lines

        val chart = findViewById<LineChartView>(R.id.chart)
        chart.lineChartData = data

    }

}