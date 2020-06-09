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
import com.atos.mobilehealthcareagent.DashBoard
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.businesslogic.TrendsBusinessLogic
import com.atos.mobilehealthcareagent.contract.TrendFragmentInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime.DateStartEnd
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime.DateStartEndForGraph
import com.atos.mobilehealthcareagent.presenter.TrendFragmentPresenter
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard.*
import kotlinx.android.synthetic.main.fragment_trends.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrendsFragment(today: Boolean) : Fragment() , TrendFragmentInterface.TrendFragmentInterfaceViewInterface {


    lateinit var db: AppDatabase
    lateinit var mTrendFragmentPresenter: TrendFragmentPresenter

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
        mTrendFragmentPresenter = TrendFragmentPresenter(this,this.context!!)

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

        mTrendFragmentPresenter.setSevenDayData("Steps")

        if(today){
            mTrendFragmentPresenter.setProgressBarData("Steps",true)
           // setStepProgressBar(circularProgressBar, TrendsBusinessLogic().todayStartTimeEndTime(), step_desc, current_steps)
            day_label.setText("Today")
        }else{
            mTrendFragmentPresenter.setProgressBarData("Steps",false)
            //setStepProgressBar(circularProgressBar, TrendsBusinessLogic().yesterdayStartTimeEndTime(), step_desc, current_steps)
            day_label.setText("Yesterday")
        }


        //Radio button set up
        weekly.setOnClickListener {

            daily_steps_chart.visibility = View.INVISIBLE
            weekly_steps_chart.visibility = View.VISIBLE
           // getWeeklyData(weekly_steps_chart)
            mTrendFragmentPresenter.setWeeklyData("Steps")

        }

        daily.setOnClickListener {
            daily_steps_chart.visibility = View.VISIBLE
            weekly_steps_chart.visibility = View.INVISIBLE
           mTrendFragmentPresenter.setSevenDayData("Steps")
        }


        btn_back_step.setOnClickListener{
            backToHealthFragment()
        }


    }
   override fun backToHealthFragment(){
        (activity as DashBoard).bottom_navigation.selectedItemId=R.id.navigation_health
    }
    fun initDatabase() {
        //Database Object Created
        db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase

    }

    override fun setProgressBar(
        progress: Float,
        currentTrendProgress: String,
        remainingGoal: String
    ) {
         circularProgressBar.progress = progress!!
        current_steps.setText(currentTrendProgress)
        if(remainingGoal.toInt()>0) {
            step_desc.setText("$remainingGoal STEPS TO GO")
        }
        else{
            step_desc.setText("YOUR GOAL ACHIEVED")
        }
    }

    //Display Daily Chart Data
    override fun displayDailyData(
        dataLabel: ArrayList<String>,
        dataValues: ArrayList<Entry>
    ) {
        val mChart:LineChart =  daily_steps_chart
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


    override fun displayWeeklyChart(dataLabel: ArrayList<String>, dataValues: ArrayList<Entry>) {
        val mChart:LineChart =  weekly_steps_chart
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
            xAxis.labelCount = 3
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            //YAxis Setup Values Setup
            /*    var yValues = ArrayList<String>(26)
                for (i in 0..25) {
                    yValues.add(i.times(1000).toString())
                }*/

            val yAxis = mChart.axisLeft
            // yAxis.valueFormatter = IAxisValueFormatter { value, axis -> yValues[(value/1000).toInt()] }
            //yAxis.granularity = 2f
            //yAxis.gridColor = Color.WHITE
            // yAxis.labelCount = 6
            //yAxis.setDrawGridLines(true)
            //yAxis.setDrawAxisLine(true)
            //yAxis.axisLineColor = Color.WHITE

            yAxis.axisMaximum = 25000f
            yAxis.axisMinimum = 0f
            yAxis.setDrawLabels(true)
            yAxis.textColor = Color.WHITE
            yAxis.textSize = 14f
            yAxis.labelCount = 3
            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)

            //Set the Data
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set)
            val data = LineData(dataSets)
            mChart.data = data
        }

    }
    override fun getMaxLabelCount(dataValues: ArrayList<Entry>): Int {
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

