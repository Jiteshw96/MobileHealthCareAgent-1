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
import kotlinx.android.synthetic.main.fragment_heartpoint_trend.*
import kotlinx.android.synthetic.main.fragment_trends.daily
import kotlinx.android.synthetic.main.fragment_trends.weekly
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HeartPointTrendFragment: Fragment() {

    lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { 
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_heartpoint_trend, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDatabase()

        //chart Setup
        weekly_heartpoint_chart.visibility = View.GONE
        weekly_heartpoint_chart.setTouchEnabled(true)
        weekly_heartpoint_chart.setPinchZoom(false)
        weekly_heartpoint_chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        weekly_heartpoint_chart.setGridBackgroundColor(Color.WHITE)
        weekly_heartpoint_chart.description.isEnabled = false
        weekly_heartpoint_chart.axisRight.gridColor = Color.WHITE
        weekly_heartpoint_chart.axisRight.setDrawLabels(false)

        daily_heartpoint_chart.setTouchEnabled(true)
        daily_heartpoint_chart.setPinchZoom(false)
        daily_heartpoint_chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        daily_heartpoint_chart.setGridBackgroundColor(Color.WHITE)
        daily_heartpoint_chart.description.isEnabled = false
        daily_heartpoint_chart.axisRight.gridColor = Color.WHITE
        daily_heartpoint_chart.axisRight.setDrawLabels(false)

        //Dummy Data For Chart
        val values = ArrayList<Entry>()
        values.add(Entry(0f, 10000f))
        values.add(Entry(1f, 20000f))
        values.add(Entry(2f, 12400f))
        values.add(Entry(3f, 11443f))
        values.add(Entry(4f, 300f))
        values.add(Entry(5f, 25000f))
        values.add(Entry(6f, 0f))
        getSevenDayData(daily_heartpoint_chart)

        todayStartTimeEndTime()
        setHeartPointProgressBar(heartPointProgressBar, todayStartTimeEndTime(), heartpoint_desc, curret_heartpoint)

        //Radio button set up
        weekly.setOnClickListener {

            daily_heartpoint_chart.visibility = View.INVISIBLE
            weekly_heartpoint_chart.visibility = View.VISIBLE
            getWeeklyData(weekly_heartpoint_chart)

        }

        daily.setOnClickListener {
            daily_heartpoint_chart.visibility = View.VISIBLE
            weekly_heartpoint_chart.visibility = View.INVISIBLE
            getSevenDayData(daily_heartpoint_chart)
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


    private fun setHeartPointProgressBar(

        circularProgressBar: CircularProgressBar,
        list: ArrayList<Long>,
        heartPointDesc: TextView,
        currentHeartPoint: TextView
    ) {
        if (db?.userDao()?.allFitnessData?.size != 0) {

            var totalHeartPoints: Double = (db?.userDao()?.getHeartPointCount(list[0], list[1]))!!.toDouble()
            Log.v("totalHeartPoints", "" + totalHeartPoints);
            val goalHeartPoints: Double = (db?.userDao()?.all?.get(0)?.goal_heartpoint!!).toDouble()
            val heartPointProgress = (totalHeartPoints?.div(goalHeartPoints!!))?.times(100)
            heartPointProgressBar.progress = heartPointProgress?.toFloat()!!

            currentHeartPoint.setText(totalHeartPoints.toInt().toString())
            val heartPointDifference = (goalHeartPoints.minus(totalHeartPoints)).toInt()
            heartpoint_desc.setText("$heartPointDifference Points Avg")
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
            var heartPoints = db?.userDao()?.getHeartPointCount(data.mStartTimeInMili, data.mEndTimeInMili)
            dataLabel.add(data.wekday)
            dataValue.add(Entry(i, heartPoints?.toFloat() ?: 0f))
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
             yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value).toInt()] }

            yAxis.setDrawLabels(true)
            yAxis.labelCount = getMaxLabelCount(dataValues)
            //yAxis.granularity = 0f
            //yAxis.gridColor = Color.WHITE
            //yAxis.setDrawGridLines(true)
            //yAxis.setDrawAxisLine(true)
            //yAxis.axisLineColor = Color.WHITE
            yAxis.textColor = Color.WHITE
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
            var heartPoint = db?.userDao()?.getHeartPointCount(data.mStartTimeInMili, data.mEndTimeInMili)
            dataLabel.add("Week ${i.toInt() + 1}")
            dataValue.add(Entry(i, heartPoint?.toFloat() ?: 0f))
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
            xAxis.xOffset = 10f
            xAxis.yOffset = 10f

            //YAxis Setup Values Setup
            var yValues = ArrayList<String>(3)
            for (i in 0..3) {
                yValues.add(i.times(10).toString())
            }

            val yAxis = mChart.axisLeft
             yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value.toInt())]}
            //yAxis.granularity = 0f
            //yAxis.gridColor = Color.WHITE
            yAxis.labelCount = 3
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