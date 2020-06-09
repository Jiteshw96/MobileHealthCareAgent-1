package com.atos.mobilehealthcareagent.contract

import android.widget.TextView
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.mikhaellopez.circularprogressbar.CircularProgressBar

interface TrendFragmentInterface {

    interface TrendFragmentInterfaceViewInterface{
        fun backToHealthFragment()
        //fun getSevenDayData(chart: LineChart)
        //fun displayDailyData(mChart: LineChart, dataLabel: ArrayList<String>, dataValues: ArrayList<Entry>)
        fun displayDailyData(dataLabel: ArrayList<String>, dataValues: ArrayList<Entry>)
       // fun getWeeklyData(chart: LineChart)
        fun displayWeeklyChart(dataLabel: ArrayList<String>, dataValues: ArrayList<Entry>)
       // fun displayWeeklyChart(mChart: LineChart,dataLabel: ArrayList<String>, dataValues: ArrayList<Entry>)
        fun getMaxLabelCount(dataValues: ArrayList<Entry>): Int
        fun setProgressBar(progress:Float,currentTrendProgress:String,remainingGoal: String)
    }

    interface TrendFragmentInterfacePresenterInterface{

        fun setProgressBarData(trendName:String,day:Boolean)
        fun setSevenDayData(trendName:String)
        fun setWeeklyData(trendName:String)

    }

    interface TrendFragmentInterfaceModelInterface{
        fun getSevenDayDataFromDatabase(db: AppDatabase,trendName:String):ArrayList<Entry>
        fun getWeeklyDataFromDatabase(db: AppDatabase,trendName:String):ArrayList<Entry>
        fun getProgressData(trendName:String,db: AppDatabase,list: ArrayList<Long>):Double
        fun getGoalData(trendName:String,db: AppDatabase):Double
        fun getSevenDayLabel(db: AppDatabase,trendName:String):ArrayList<String>
        fun getWeekLabel(db: AppDatabase,trendName:String):ArrayList<String>
    }

}