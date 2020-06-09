package com.atos.mobilehealthcareagent.model

import androidx.room.Database
import com.atos.mobilehealthcareagent.contract.TrendFragmentInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.github.mikephil.charting.data.Entry

class TrendFragmentModel: TrendFragmentInterface.TrendFragmentInterfaceModelInterface {


    override fun getSevenDayDataFromDatabase(db: AppDatabase,trendName:String):ArrayList<Entry> {
        val dataList: ArrayList<GetDateDetailsStartEndTime.DateStartEndForGraph> = GetDateDetailsStartEndTime().ListOfDaysForGraph(7)
        val dataValue = ArrayList<Entry>()

        when(trendName){

            "Distance" -> {
                var i = 0f
                for (data in dataList) {
                    var distance = db.userDao()?.getDistanceCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, ((distance?.toFloat() ?: 0f)/1000.0).toFloat()    ))
                    i++

                }

            }

            "Steps" ->{
                var i = 0f
                for (data in dataList) {
                    var steps = db.userDao()?.getStepCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, steps?.toFloat() ?: 0f))
                    i++
                }
            }

            "HeartPoint" -> {
                var i = 0f
                for (data in dataList) {
                    var heartPoints = db.userDao()?.getHeartPointCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, heartPoints?.toFloat() ?: 0f))
                    //  dataValue.add(Entry(i,i.times(200)))
                    i++
                }
            }

            "Calorie" -> {
                 var i = 0f
                 for(data in dataList) {
                    var calroies = db.userDao()?.getCalorieCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, calroies?.toFloat() ?: 0f))
                    i++

                }

            }

        }



        return  dataValue
    }

    override fun getSevenDayLabel(db: AppDatabase,trendName:String): ArrayList<String> {
        val dataList: ArrayList<GetDateDetailsStartEndTime.DateStartEndForGraph> = GetDateDetailsStartEndTime().ListOfDaysForGraph(7)
        val dataLabel = ArrayList<String>()
        var i = 0f
        for (data in dataList) {
            dataLabel.add(data.wekday)
            i++

        }
        return  dataLabel
    }

    override fun getWeeklyDataFromDatabase(db: AppDatabase,trendName:String): ArrayList<Entry> {
        val dataList: ArrayList<GetDateDetailsStartEndTime.DateStartEnd> = GetDateDetailsStartEndTime().ListOfWeekForGraph(4)
        val dataValue = ArrayList<Entry>()
        when(trendName){
            "Distance" -> {
                var i = 0f
                for (data in dataList) {
                    var distance = db.userDao()?.getDistanceCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, ((distance?.toFloat() ?: 0f)/1000.0).toFloat()     ))
                    i++
                }
            }

            "Steps" ->{
                var i = 0f
                for (data in dataList) {
                    var steps = db.userDao()?.getStepCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, steps?.toFloat() ?: 0f))
                    i++
                }
            }

            "HeartPoint" -> {
                var i = 0f
                for (data in dataList) {
                    var distance = db.userDao()?.getHeartPointCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, ((distance?.toFloat() ?: 0f)/1000.0).toFloat()     ))
                    i++
                }
            }

            "Calorie" -> {
                var i = 0f
                for (data in dataList) {
                    var  calories = db.userDao()?.getCalorieCount(data.mStartTimeInMili, data.mEndTimeInMili)
                    dataValue.add(Entry(i, calories?.toFloat() ?: 0f))
                    i++
                }

            }

        }
        return dataValue
    }

    override fun getWeekLabel(db: AppDatabase,trendName:String): ArrayList<String> {
        val dataList: ArrayList<GetDateDetailsStartEndTime.DateStartEndForGraph> = GetDateDetailsStartEndTime().ListOfDaysForGraph(7)
        val dataLabel = ArrayList<String>()
        var i = 0f
        for (data in dataList) {
            dataLabel.add("Week ${i.toInt() + 1}")
            i++
        }
        return  dataLabel
    }

    override fun getProgressData(trendName:String,db:AppDatabase,list: ArrayList<Long>):Double {
        var totalProgress = 0.0
        if (db?.userDao()?.allFitnessData?.size != 0) {
            when(trendName){

                "Distance" -> {
                    totalProgress = (db.userDao()?.getDistanceCount(list[0], list[1]))!!.toDouble()
                }

                "Steps" ->{
                    totalProgress = (db.userDao()?.getStepCount(list[0], list[1]))!!.toDouble()
                }

                "HeartPoint" -> {
                    totalProgress = (db.userDao()?.getHeartPointCount(list[0], list[1]))!!.toDouble()
                }

                "Calorie" -> {

                    totalProgress = (db.userDao()?.getCalorieCount(list[0], list[1]))!!.toDouble()
                }

            }
        }
        return totalProgress
    }

    override fun getGoalData(trendName: String, db: AppDatabase):Double {
        var goal:Double = 0.0
        if (db?.userDao()?.allFitnessData?.size != 0) {
            when(trendName){

                "Distance" -> {
                    goal = (db?.userDao()?.all?.get(0)?.goal_distance!!).toDouble()
                }

                "Steps" ->{
                    goal = (db?.userDao()?.all?.get(0)?.goal_steps!!).toDouble()
                }

                "HeartPoint" -> {
                    goal = (db?.userDao()?.all?.get(0)?.goal_heartpoint!!).toDouble()
                }

                "Calorie" -> {

                    goal = (db?.userDao()?.all?.get(0)?.goal_calorie!!).toDouble()
                }

            }
        }

        return goal
    }
}