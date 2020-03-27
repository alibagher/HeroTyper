package com.phatphoophoo.pdtran.herotyper.views.stats_views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.phatphoophoo.pdtran.herotyper.R
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import lecho.lib.hellocharts.view.LineChartView


class SpeedStatsView: LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, attributeSetId: Int) : super(context, attrs, attributeSetId)

    init {

        inflate(context, R.layout.speed_stats_layout, this)

        val values = mutableListOf<PointValue>()
        values.add(PointValue(0F, 2F))
        values.add(PointValue(1F, 4F))
        values.add(PointValue(2F, 3F))
        values.add(PointValue(3F, 4F))

        val line = Line(values).setColor(R.color.colorPrimaryDark).setCubic(true)
        val lines = arrayListOf<Line>(line)

        val data = LineChartData()
        data.lines = lines

        val axisX = Axis()
        val axisY = Axis().setHasLines(true)
        axisX.name = "Game Number"
        axisY.name = "WPM"
        data.axisXBottom = axisX
        data.axisYLeft = axisY


        val chart = findViewById<LineChartView>(R.id.chart)
        chart.lineChartData = data

    }

}