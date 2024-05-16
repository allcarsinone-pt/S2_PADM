package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.R

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry



class StandStatisticsActivity : AppCompatActivity() {
    lateinit var barChart: BarChart
    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntriesList: ArrayList<BarEntry>

    lateinit var pieChart: PieChart
    lateinit var pieData: PieData
    lateinit var pieDataSet: PieDataSet
    lateinit var pieEntriesList: ArrayList<PieEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stand_statistics)

        loadBarChart()
        loadPieChart()
    }
    fun loadBarChart(){
        barChart = findViewById(R.id.StandStatisticsChart)
        getBarChartData()
        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setColor(resources.getColor(R.color.red))
        barDataSet.valueTextSize = 16f
        barChart.description.isEnabled = false
    }

    fun loadPieChart(){
        pieChart = findViewById(R.id.StandStatisticsPieChart)
        getPieChartData()
        pieDataSet = PieDataSet(pieEntriesList, "Pie Chart Data")
        pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.setColor(resources.getColor(R.color.red))
        pieDataSet.valueTextSize = 16f
        pieChart.description.isEnabled = false
    }

    private fun getBarChartData() {
        barEntriesList = ArrayList()
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList.add(BarEntry(2f, 2f))
        barEntriesList.add(BarEntry(3f, 3f))
        barEntriesList.add(BarEntry(4f, 4f))
        barEntriesList.add(BarEntry(5f, 5f))
    }

    private fun getPieChartData() {
        pieEntriesList = ArrayList()
        pieEntriesList.add(PieEntry(1f, 1f))
        pieEntriesList.add(PieEntry(2f, 2f))
        pieEntriesList.add(PieEntry(3f, 3f))
        pieEntriesList.add(PieEntry(4f, 4f))
        pieEntriesList.add(PieEntry(5f, 5f))
    }
}