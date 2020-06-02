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
import com.atos.mobilehealthcareagent.businesslogic.TrendsBusinessLogic
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime.DateStartEnd
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime.DateStartEndForGraph
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
import kotlinx.android.synthetic.main.fragment_trends.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrendsFragment(today: Boolean) : Fragment() {


    lateinit var db: AppDatabase

    var today=true
    init {
        this.today=today
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trends, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDatabase()

        //chart Setup
        weekly_steps_chart.visibility = View.GONE
        weekly_steps_chart.setTouchEnabled(false)
        weekly_steps_chart.setPinchZoom(false)
        weekly_steps_chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        weekly_steps_chart.setGridBackgroundColor(Color.WHITE)
        weekly_steps_chart.description.isEnabled = false
        weekly_steps_chart.axisRight.gridColor = Color.WHITE
        weekly_steps_chart.axisRight.setDrawLabels(false)
        weekly_steps_chart.extraRightOffset = 22f

        daily_steps_chart.setTouchEnabled(false)
        daily_steps_chart.setPinchZoom(false)
        daily_steps_chart.setBackgroundColor(Color.parseColor("#99BABABA"))
        daily_steps_chart.setGridBackgroundColor(Color.WHITE)
        daily_steps_chart.description.isEnabled = false
        daily_steps_chart.axisRight.gridColor = Color.WHITE
        daily_steps_chart.axisRight.setDrawLabels(false)

        //Dummy Data For Chart
        val values = ArrayList<Entry>()
        values.add(Entry(0f, 10000f))
        values.add(Entry(1f, 20000f))
        values.add(Entry(2f, 12400f))
        values.add(Entry(3f, 11443f))
        values.add(Entry(4f, 300f))
        values.add(Entry(5f, 25000f))
        values.add(Entry(6f, 0f))
        getSevenDayData(daily_steps_chart)

        if(today){
            setStepProgressBar(circularProgressBar, TrendsBusinessLogic().todayStartTimeEndTime(), step_desc, current_steps)
            day_label.setText("Today")
        }else{
            setStepProgressBar(circularProgressBar, TrendsBusinessLogic().yesterdayStartTimeEndTime(), step_desc, current_steps)
            day_label.setText("Yesterday")
        }


        //Radio button set up
        weekly.setOnClickListener {

            daily_steps_chart.visibility = View.INVISIBLE
            weekly_steps_chart.visibility = View.VISIBLE
            getWeeklyData(weekly_steps_chart)

        }

        daily.setOnClickListener {
            daily_steps_chart.visibility = View.VISIBLE
            weekly_steps_chart.visibility = View.INVISIBLE
            getSevenDayData(daily_steps_chart)
        }

    }

    fun initDatabase() {
        //Database Object Created
        db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase

    }
    private fun setStepProgressBar(
        circularProgressBar: CircularProgressBar,
        list: ArrayList<Long>,
        stepDesc: TextView,
        currentSteps: TextView
    ) {
        if (db?.userDao()?.allFitnessData?.size != 0) {

            var totalSteps: Double = (db?.userDao()?.getStepCount(list[0], list[1]))!!.toDouble()
            //var totalSteps:Double = 2700.toDouble()
            Log.v("totalSteps", "" + totalSteps);
            val goalSteps: Double = (db?.userDao()?.all?.get(0)?.goal_steps!!).toDouble()
            val stepProgress = (totalSteps?.div(goalSteps!!))?.times(100)
            circularProgressBar.progress = stepProgress?.toFloat()!!

            currentSteps.setText(totalSteps.toInt().toString())
            val stepDifference = (goalSteps.minus(totalSteps)).toInt()
            stepDesc.setText("$stepDifference STEPS TO GO")
        }
    }

    private fun getSevenDayData(chart: LineChart) {

        //Refresh the chart
       // chart.notifyDataSetChanged();
        chart.invalidate();
        chart.clear()
        val dataList: ArrayList<DateStartEndForGraph> =
            GetDateDetailsStartEndTime().ListOfDaysForGraph(7)

        val dataLabel = ArrayList<String>()
        val dataValue = ArrayList<Entry>()
        var i = 0f

        for (data in dataList) {
            var steps = db?.userDao()?.getStepCount(data.mStartTimeInMili, data.mEndTimeInMili)
            dataLabel.add(data.wekday)
            dataValue.add(Entry(i, steps?.toFloat() ?: 0f))
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
            xAxis.textSize = 14f
            xAxis.position = XAxis.XAxisPosition.BOTTOM

           /* //YAxis Setup
            var yValues = ArrayList<String>(26)
            for (i in 0..25) {
                yValues.add(i.times(100).toString())
            }*/

            val yAxis = mChart.axisLeft
          //  yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value / 100).toInt()] }

            yAxis.setDrawLabels(true)
            yAxis.labelCount = getMaxLabelCount(dataValues)
            //yAxis.granularity = 0f
            //yAxis.gridColor = Color.WHITE
            //yAxis.setDrawGridLines(true)
            //yAxis.setDrawAxisLine(true)
            //yAxis.axisLineColor = Color.WHITE
            yAxis.axisMinimum = 0f
            //yAxis.axisMaximum = 15000f
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
        val dataList: ArrayList<DateStartEnd> = GetDateDetailsStartEndTime().ListOfWeekForGraph(4)
        val dataLabel = ArrayList<String>()
        val dataValue = ArrayList<Entry>()
        var i = 0f

        for (data in dataList) {
            var steps = db?.userDao()?.getStepCount(data.mStartTimeInMili, data.mEndTimeInMili)
            dataLabel.add("Week ${i.toInt() + 1}")
            dataValue.add(Entry(i, steps?.toFloat() ?: 0f))
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
            xAxis.textSize = 14f
            xAxis.labelCount = 4
            xAxis.position = XAxis.XAxisPosition.BOTTOM

          /*  //YAxis Setup Values Setup
            var yValues = ArrayList<String>(3)
            for (i in 0..3) {
                yValues.add(i.times(10000).toString())
            }*/

            val yAxis = mChart.axisLeft
            //yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value / 10000).toInt()] }
            //yAxis.granularity = 0f
            //yAxis.gridColor = Color.WHITE
            //yAxis.labelCount = 3
            //yAxis.setDrawGridLines(true)
            //yAxis.setDrawAxisLine(true)
            //yAxis.axisLineColor = Color.WHITE

          //  yAxis.axisMaximum = 50000f
            yAxis.axisMinimum = 0f
            yAxis.setDrawLabels(true)
            yAxis.textColor = Color.WHITE
            yAxis.textSize = 14f
          //  yAxis.labelCount = 3
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

