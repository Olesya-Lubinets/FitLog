package com.example.fitlog

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis.XAxisPosition.*
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object ChartManager {

    private fun drawChart(chart:LineChart ,data: List<Pair<LocalDate,Int>>,title:String) {

        val entries = data.mapIndexed { index,pair ->
          Entry(index.toFloat(), pair.second.toFloat())
        }
        val dateLabels = data.map {
            it.first.format(DateTimeFormatter.ofPattern("MM-dd"))
        }

        val dataSet = LineDataSet(entries, title)
        chart.data = LineData(adjustDataSetAppearance(dataSet))

        val xAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(dateLabels)
        xAxis.granularity = 1f
        xAxis.position = BOTTOM
        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false
    }

    private fun adjustDataSetAppearance(dataSet:LineDataSet):LineDataSet {
        dataSet.color = Color.parseColor("#4CAF50")
        dataSet.lineWidth = 4f
        dataSet.circleRadius = 5f
        dataSet.setCircleColor(Color.parseColor("#4CAF50"))
        dataSet.setDrawValues(false)
        return dataSet
    }

    fun drawFoodChart(chart:LineChart, data: List<Pair<LocalDate,Int>>) =
        drawChart(chart, data, "Calories eaten")

    fun drawWorkoutChart(chart:LineChart, data: List<Pair<LocalDate,Int>>)  =
        drawChart(chart, data, "Calories burned")
}