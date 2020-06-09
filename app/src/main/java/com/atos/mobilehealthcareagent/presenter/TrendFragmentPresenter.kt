package com.atos.mobilehealthcareagent.presenter

import android.content.Context
import com.atos.mobilehealthcareagent.businesslogic.TrendsBusinessLogic
import com.atos.mobilehealthcareagent.contract.TrendFragmentInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.fragments.DistanceTrendFragment
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.atos.mobilehealthcareagent.model.TrendFragmentModel
import com.github.mikephil.charting.data.Entry
import java.text.DecimalFormat

class TrendFragmentPresenter:TrendFragmentInterface.TrendFragmentInterfacePresenterInterface {

    lateinit var db: AppDatabase
    var mTrendFragmentInterfaceViewInterface: TrendFragmentInterface.TrendFragmentInterfaceViewInterface
    var context:Context
    var mTrendFragmentModel: TrendFragmentModel
    val df = DecimalFormat("#.##")

    constructor(
        mTrendFragmentInterfaceViewInterface: TrendFragmentInterface.TrendFragmentInterfaceViewInterface,
        context: Context
    ){
        this.mTrendFragmentModel = TrendFragmentModel()
        this.mTrendFragmentInterfaceViewInterface = mTrendFragmentInterfaceViewInterface
        this.context = context
        init()
    }

    private fun init(){
        db = AppDatabase.getAppDatabase(context) as AppDatabase
    }

    override fun setProgressBarData(trendName:String,day:Boolean) {
        var totalProgress:Double
        if(day){
            totalProgress = mTrendFragmentModel.getProgressData(trendName,db,TrendsBusinessLogic().todayStartTimeEndTime())
        }else{
            totalProgress = mTrendFragmentModel.getProgressData(trendName,db,TrendsBusinessLogic().yesterdayStartTimeEndTime())
        }
        var goal = mTrendFragmentModel.getGoalData(trendName,db)
        var remainingProgress =  (goal.minus(totalProgress)).toInt()
        var barProgress = (totalProgress?.div(goal!!))?.times(100)

        if(trendName == "Distance"){
            val totalProgressInKm:String = df.format((totalProgress!!/1000.0f))
            val differenceDistanceInKm = df.format((remainingProgress!!/1000.0f))
            mTrendFragmentInterfaceViewInterface.setProgressBar(barProgress.toFloat(),totalProgressInKm,differenceDistanceInKm)

        }else{
            mTrendFragmentInterfaceViewInterface.setProgressBar(barProgress.toFloat(),totalProgress.toInt().toString(),remainingProgress.toString())
        }
    }

    override fun setSevenDayData(trendName:String){

        var dataLabel = ArrayList<String>()
        var dataValue = ArrayList<Entry>()
        dataLabel = mTrendFragmentModel.getSevenDayLabel(db,trendName)
        dataValue = mTrendFragmentModel.getSevenDayDataFromDatabase(db,trendName)
        mTrendFragmentInterfaceViewInterface.displayDailyData(dataLabel,dataValue)

    }

    override fun setWeeklyData(trendName:String) {

        var dataLabel = ArrayList<String>()
        var dataValue = ArrayList<Entry>()
        dataLabel = mTrendFragmentModel.getWeekLabel(db,trendName)
        dataValue = mTrendFragmentModel.getWeeklyDataFromDatabase(db,trendName)
        mTrendFragmentInterfaceViewInterface.displayWeeklyChart(dataLabel,dataValue)


    }
}