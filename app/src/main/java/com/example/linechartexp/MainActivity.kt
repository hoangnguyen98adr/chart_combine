package com.example.linechartexp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initChart(lineChart = lineChart)
        initChart(cbChart)
        val listData = listOf(LineDataModel("Operating", listOf("2", "5", " 9", "3")),
            LineDataModel("Investing", listOf("1", "3", " 2", "9")),
            LineDataModel("Financing", listOf("4", "0", " 2", "2")))
        updateDataForChart(generateLineData(listData))
    }

    private fun updateDataForChart(generateLineData: LineData) {
        lineChart.apply {
            data = generateLineData
            invalidate()
        }
    }

    private fun initChart(combinedChart: CombinedChart? = null, lineChart: LineChart? = null) {
        val label = listOf("2017", "2018", "2019", "2020")
        when {
            combinedChart != null -> initBaseChart(combinedChart)
        }
        combinedChart.let { chart ->
            chart.description.isEnabled = false
            chart.setTouchEnabled(false)
            chart.setScaleEnabled(false)
            chart.setDrawGridBackground(false)
            chart.setPinchZoom(false)
            chart.legend.apply {
                isEnabled = true
                isWordWrapEnabled = true
                applicationContext?.let { textColor = ContextCompat.getColor(it, R.color.white) }
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                /*orientation = Legend.LegendOrientation.HORIZONTAL*/
            }
            chart.xAxis.apply {
                setCenterAxisLabels(true)
                position = XAxis.XAxisPosition.BOTTOM
                axisMinimum = 0f
                axisMaximum = 4F
                granularity = 1F
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(label)
                applicationContext?.let { textColor = ContextCompat.getColor(it, R.color.white) }
            }
            chart.axisLeft.isEnabled = false
            chart.axisRight.apply {
                axisMinimum = 0f
                setDrawAxisLine(false)
                applicationContext?.let { textColor = ContextCompat.getColor(it, R.color.white) }
            }
        }
    }

    data class LineDataModel(
        val description: String,
        val values: List<String>
    )

    private fun generateLineData(list: List<LineDataModel>): LineData {
        val lineDataSet1 = LineDataSet(getLineDataEntries(list, "Operating"), "Operating").apply { configLineData(this, "Line1") }
        val lineDataSet2 = LineDataSet(getLineDataEntries(list, "Investing"), "Investing").apply { configLineData(this, "Line2") }
        val lineDataSet3 = LineDataSet(getLineDataEntries(list, "Financing"), "Financing").apply { configLineData(this, "Line3") }
        return LineData(listOf(lineDataSet1, lineDataSet2, lineDataSet3))
    }

    private fun getLineDataEntries(
        list: List<LineDataModel>,
        type: String
    ): List<Entry>? {
        return list.find { it.description == type }?.values?.mapIndexed { index, s -> Entry(index.toFloat() + 0.5F, s.toFloat())  }
    }

    private fun configLineData(
        lineDataSet: LineDataSet,
        type: String
    ) {
        lineDataSet.apply {
            mode = LineDataSet.Mode.LINEAR
            setDrawValues(false)
            applicationContext?.let {
                color = when(type) {
                    "Line1" -> ContextCompat.getColor(it, R.color.blue2685DB)
                    "Line2" -> ContextCompat.getColor(it, R.color.green2DD0D0)
                    "Line3" -> ContextCompat.getColor(it, R.color.yellowF5973F)
                    else -> Color.WHITE
                }
            }
            setDrawCircles(false)
            lineWidth = 1f
        }
    }
}