package com.atos.mobilehealthcareagent.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.fragment_calories_trend.*
import kotlinx.android.synthetic.main.fragment_trends.daily
import kotlinx.android.synthetic.main.fragment_trends.weekly
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CaloriesTrendFragment: Fragment() {

    lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calories_trend, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDatabase()

        //chart Setup
        weekly_calories_chart.visibility = View.GONE
        weekly_calories_chart.setTouchEnabled(true)
        weekly_calories_chart.setPinchZoom(false)
        weekly_calories_chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        weekly_calories_chart.setGridBackgroundColor(Color.WHITE)
        weekly_calories_chart.description.isEnabled = false
        weekly_calories_chart.axisRight.gridColor = Color.WHITE
        weekly_calories_chart.axisRight.setDrawLabels(false)

        daily_calories_chart.setTouchEnabled(true)
        daily_calories_chart.setPinchZoom(false)
        daily_calories_chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        daily_calories_chart.setGridBackgroundColor(Color.WHITE)
        daily_calories_chart.description.isEnabled = false
        daily_calories_chart.axisRight.gridColor = Color.WHITE
        daily_calories_chart.axisRight.setDrawLabels(false)

        //Dummy Data For Chart
        val values = ArrayList<Entry>()
        values.add(Entry(0f, 10000f))
        values.add(Entry(1f, 20000f))
        values.add(Entry(2f, 12400f))
        values.add(Entry(3f, 11443f))
        values.add(Entry(4f, 300f))
        values.add(Entry(5f, 25000f))
        values.add(Entry(6f, 0f))
        getSevenDayData(daily_calories_chart)

        todayStartTimeEndTime()
        setCaloriesProgressBar(caloriesProgressBar, todayStartTimeEndTime(), calories_desc, current_calories)

        //Radio button set up
        weekly.setOnClickListener {

            daily_calories_chart.visibility = View.INVISIBLE
            weekly_calories_chart.visibility = View.VISIBLE
            getWeeklyData(weekly_calories_chart)

        }

        daily.setOnClickListener {
            daily_calories_chart.visibility = View.VISIBLE
            weekly_calories_chart.visibility = View.INVISIBLE
            getSevenDayData(daily_calories_chart)
        }

    }

    fun initDatabase() {
        //Database Object Created
        db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase

    }

    //Get Time for Today
    fun getToday(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return formatter.format(date)
    }

    fun todayStartTimeEndTime(): ArrayList<Long> {
        var returnValue = ArrayList<Long>()
        val myStartDate = getToday() + " 00:00:01"
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = sdf.parse(myStartDate)
        val startMilisecond = date.time
        returnValue.add(startMilisecond)
        val myEndDate = getToday() + " 23:59:59"
        val Enddate = sdf.parse(myEndDate)
        val endtMilisecond = Enddate.time
        returnValue.add(endtMilisecond)
        return returnValue
    }


    private fun setCaloriesProgressBar(
        circularProgressBar: CircularProgressBar,
        list: ArrayList<Long>,
        caloriesDesc: TextView,
        currentCalories: TextView
    ) {
        if (db?.userDao()?.allFitnessData?.size != 0) {

            var totalCalories: Double = (db?.userDao()?.getCalorieCount(list[0], list[1]))!!.toDouble()

            Log.v("totalCalories", "" + totalCalories);
            val goalCalories: Double = (db?.userDao()?.all?.get(0)?.goal_calorie!!).toDouble()
            val caloriesProgress = (totalCalories?.div(goalCalories!!))?.times(100)
            caloriesProgressBar.progress = caloriesProgress?.toFloat()!!

            currentCalories.setText(totalCalories.toInt().toString())
            val caloriesDifference = (goalCalories.minus(totalCalories)).toInt()
            caloriesDesc.setText("$caloriesDifference Kcal/Day")
        }
    }

    private fun getSevenDayData(chart: LineChart) {

        //Refresh the chart
        chart.notifyDataSetChanged();
        chart.invalidate();
        chart.clear()
        val dataList: ArrayList<GetDateDetailsStartEndTime.DateStartEndForGraph> =
            GetDateDetailsStartEndTime().ListOfDaysForGraph(7)

        val dataLabel = ArrayList<String>()
        val dataValue = ArrayList<Entry>()
        var i = 0f

        for (data in dataList) {
            var calroies = db?.userDao()?.getCalorieCount(data.mStartTimeInMili, data.mEndTimeInMili)
            dataLabel.add(data.wekday)
            dataValue.add(Entry(i, calroies?.toFloat() ?: 0f))
            //  dataValue.add(Entry(i,i.times(200)))
             i++

        }
        displayDailyData(chart, dataLabel, dataValue)
    }

    //Display Daily Chart Data
    private fun displayDailyData(
        mChart: LineChart,
        dataLabel: ArrayList<String>,
        dataValues: ArrayList<Entry>
    ) {
        val set: LineDataSet

        if (mChart.data != null && mChart.data.dataSetCount > 0) {
            set = mChart.data.getDataSetByIndex(0) as LineDataSet
            set.values = dataValues
            mChart.data.notifyDataChanged()
            mChart.notifyDataSetChanged()

        } else {
            set = LineDataSet(dataValues, "")
            set.setDrawIcons(false)
            set.color = Color.WHITE
            set.setCircleColor(Color.WHITE)
            set.lineWidth = 2f
            set.circleRadius = 4f
            set.setDrawCircleHole(false)
            set.setDrawValues(false)
            set.setDrawFilled(true)
            set.formLineWidth = 1f


            if (Utils.getSDKInt() >= 18) {
                val drawable =
                    ContextCompat.getDrawable(activity!!, R.drawable.fade_orange_refactor)
                set.fillDrawable = drawable
            } else {
                set.fillColor = Color.DKGRAY
            }


            //Dummy Values for X-axis
            //  val xValues = arrayOf("S", "M", "T", "W", "T", "F", "S")
            val xValues = dataLabel

            //xAxis Setup
            val xAxis = mChart.xAxis
            //xAxis.granularity = 0f
            xAxis.valueFormatter = IAxisValueFormatter { value, axis -> xValues[value.toInt()] }
            xAxis.gridColor = Color.WHITE
            xAxis.textColor = Color.WHITE
            xAxis.textSize = 12f
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            //YAxis Setup
            var yValues = ArrayList<String>(5)
            for (i in 0..5) {
                yValues.add(i.times(100).toString())
            }

            val yAxis = mChart.axisLeft
           // yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value / 100).toInt()] }

            yAxis.setDrawLabels(true)
            yAxis.labelCount = getMaxLabelCount(dataValues)
            //yAxis.granularity = 0f
            //yAxis.gridColor = Color.WHITE
            //yAxis.setDrawGridLines(true)
            //yAxis.setDrawAxisLine(true)
            //yAxis.axisLineColor = Color.WHITE
            yAxis.textColor = Color.WHITE
            yAxis.textSize = 14f
            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

            //Set the Data
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set)
            val data = LineData(dataSets)
            mChart.data = data

        }

    }

    private fun getWeeklyData(chart: LineChart) {

        //Refresh the chart
        chart.notifyDataSetChanged()
        chart.invalidate()
        chart.clear()
        val dataList: ArrayList<GetDateDetailsStartEndTime.DateStartEnd> = GetDateDetailsStartEndTime().ListOfWeekForGraph(4)
        val dataLabel = ArrayList<String>()
        val dataValue = ArrayList<Entry>()
        var i = 0f

        for (data in dataList) {
            var  calories = db?.userDao()?.getCalorieCount(data.mStartTimeInMili, data.mEndTimeInMili)
            dataLabel.add("Week ${i.toInt() + 1}")
            dataValue.add(Entry(i, calories?.toFloat() ?: 0f))
            // dataValue.add(Entry(i,i.times(200)))
            i++
        }
        displayWeeklyChart(chart, dataLabel, dataValue)
    }

    //Display weekly ChartData
    private fun displayWeeklyChart(
        mChart: LineChart,
        dataLabel: ArrayList<String>,
        dataValues: ArrayList<Entry>
    ) {
        val set: LineDataSet

        if (mChart.data != null && mChart.data.dataSetCount > 0) {
            set = mChart.data.getDataSetByIndex(0) as LineDataSet
            set.values = dataValues
            mChart.data.notifyDataChanged()
            mChart.notifyDataSetChanged()

        } else {
            set = LineDataSet(dataValues, "")
            set.setDrawIcons(false)
            set.color = Color.WHITE
            set.setCircleColor(Color.WHITE)
            set.lineWidth = 2f
            set.circleRadius = 4f
            set.setDrawCircleHole(false)
            set.setDrawValues(false)
            set.setDrawFilled(true)
            set.formLineWidth = 1f

            if (Utils.getSDKInt() >= 18) {
                val drawable =
                    ContextCompat.getDrawable(activity!!, R.drawable.fade_orange_refactor)
                set.fillDrawable = drawable
            } else {
                set.fillColor = Color.DKGRAY
            }
            //Dummy Values for X-axis
            //  val xValues = arrayOf("S", "M", "T", "W", "T", "F", "S")
            val xValues = dataLabel

            //xAxis Setup
            val xAxis = mChart.xAxis
            //xAxis.granularity = 0f
            xAxis.valueFormatter = IAxisValueFormatter { value, axis -> xValues[value.toInt()] }
            xAxis.gridColor = Color.WHITE
            xAxis.textColor = Color.WHITE
            xAxis.textSize = 12f
            xAxis.labelCount = 3
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            //YAxis Setup Values Setup
            var yValues = ArrayList<String>(3)
            for (i in 0..3) {
                yValues.add(i.times(10000).toString())
            }

            val yAxis = mChart.axisLeft
            yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value / 10000).toInt()] }
            //yAxis.granularity = 0f
            //yAxis.gridColor = Color.WHITE
            //yAxis.labelCount = 3
            //yAxis.setDrawGridLines(true)
            //yAxis.setDrawAxisLine(true)
            //yAxis.axisLineColor = Color.WHITE

            yAxis.setDrawLabels(true)
            yAxis.textColor = Color.WHITE
            yAxis.textSize = 14f
            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

            //Set the Data
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set)
            val data = LineData(dataSets)
            mChart.data = data
        }

    }
    private fun getMaxLabelCount(dataValues: ArrayList<Entry>): Int {
        var maxLabelCount = 0f
        for (data in dataValues) {
            if (data.y > maxLabelCount) {
                maxLabelCount = data.y
            }
        }
        maxLabelCount = maxLabelCount.div(300)
        //if (maxLabelCount.toInt() < 5)  maxLabelCount.toInt()+1 else 5
        return 3
    }

}