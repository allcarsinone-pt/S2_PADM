package com.allcarsinone.allcarsinone.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.allcarsinone.allcarsinone.R

import android.graphics.Color
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class StandStatisticsActivity : AppCompatActivity() {
    private lateinit var barChart: BarChart
    private lateinit var barData: BarData
    private lateinit var barDataSet: BarDataSet
    private lateinit var barEntriesList: ArrayList<BarEntry>

    private lateinit var pieChart: PieChart
    private lateinit var pieData: PieData
    private lateinit var pieDataSet: PieDataSet
    private lateinit var pieEntriesList: ArrayList<PieEntry>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stand_statistics)

        val spinnerTop = findViewById<Spinner>(R.id.StandsStatisticsTop_Spinner_SP)
        val spinnerBot = findViewById<Spinner>(R.id.StandsStatisticsBottom_Spinner_SP)
        val itemsPeriod = arrayOf("Today", "This week", "This month", "Max")
        val adapterMon = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsPeriod )
        spinnerTop.setAdapter(adapterMon)
        spinnerBot.setAdapter(adapterMon)

        loadBarChart()
        loadPieChart()
    }
    private fun loadPieChart(){
        pieChart = findViewById(R.id.StandStatisticsPieChart)
        getPieChartData()
        pieDataSet = PieDataSet(pieEntriesList, "Vehicles availability")
        pieData = PieData(pieDataSet)
        pieChart.data = pieData
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.setColors(resources.getColor(R.color.red), resources.getColor(R.color.greenChart))
        pieDataSet.valueTextSize = 16f
        pieChart.description.isEnabled = false
    }
    private fun loadBarChart(){
        barChart = findViewById(R.id.StandStatisticsChart)
        getBarChartData()
        barDataSet = BarDataSet(barEntriesList, "Vehicles sales")
        barData = BarData(barDataSet)
        barChart.data = barData
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setColor(resources.getColor(R.color.red))
        barDataSet.valueTextSize = 16f
        barChart.description.isEnabled = false
    }
    private fun getPieChartData() {
        pieEntriesList = ArrayList()
        pieEntriesList.add(PieEntry(1f, 1f))
        pieEntriesList.add(PieEntry(2f, 2f))
    }
    private fun getBarChartData() {
        barEntriesList = ArrayList()
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList.add(BarEntry(2f, 2f))
        barEntriesList.add(BarEntry(3f, 3f))
        barEntriesList.add(BarEntry(4f, 4f))
        barEntriesList.add(BarEntry(5f, 5f))
    }
}