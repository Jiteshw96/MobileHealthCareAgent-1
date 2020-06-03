package com.atos.mobilehealthcareagent.presenter

import android.content.Context
import android.util.Log
import com.atos.mobilehealthcareagent.businesslogic.DashBoardBuissnessLogic
import com.atos.mobilehealthcareagent.contract.HealthFragmentInterface
import com.atos.mobilehealthcareagent.contract.HealthFragmentInterface.HealthFragmentInterfaceViewInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.model.HealthFragmentModel
import java.math.RoundingMode
import java.text.DecimalFormat

class HealthFragmentPresenter :
    HealthFragmentInterface.HealthFragmentInterfacePresenterInterface {

    lateinit var mHealthFragmentInterfaceViewInterface: HealthFragmentInterfaceViewInterface
    lateinit var context:Context
    lateinit var db: AppDatabase
    lateinit var mHealthFragmentModel: HealthFragmentModel

    constructor(
        mHealthFragmentInterfaceViewInterface: HealthFragmentInterfaceViewInterface,
        context: Context
    ) {

        this.mHealthFragmentInterfaceViewInterface=mHealthFragmentInterfaceViewInterface
        this.context=context

        mHealthFragmentModel=HealthFragmentModel()

        initialize()
    }

    override fun initialize() {
        db = AppDatabase.getAppDatabase(context) as AppDatabase

        mHealthFragmentInterfaceViewInterface.initView()
        mHealthFragmentInterfaceViewInterface. todayOperation()
        getProgressGraph(DashBoardBuissnessLogic().todayStartTimeEndTime())

    }

    override fun getUserInfo(db: AppDatabase): User? {
        return mHealthFragmentModel.getUserInfo(db)?.get(0)
    }

    override fun getProgressGraph(list:ArrayList<Long>){

     //   if(mHealthFragmentModel.getFitnessDataCount(db)?.size!!>0){

           // TODO("Check the double type conversion")
            var totalBurnCalorie = mHealthFragmentModel.getCalorieCount(list.get(0), list.get(1),db)
            var totalSteps = mHealthFragmentModel.getStepCount(list.get(0), list.get(1),db)
            var totalDistance = mHealthFragmentModel.getDistanceCount(list.get(0), list.get(1),db)
            var totalHeartoint = mHealthFragmentModel.getHeartPointCount(list.get(0), list.get(1),db)

            Log.v("totalBurnCalorie", "" + totalBurnCalorie);
            Log.v("totalSteps", "" + totalSteps);
            Log.v("totalDistance", "" + totalDistance);
            Log.v("totalHeartoint", "" + totalHeartoint);
            mHealthFragmentModel.getUserInfo(db)

            val goalSteps =   mHealthFragmentModel.getUserInfo(db)?.get(0)?.goal_steps
            val goalCalories =   mHealthFragmentModel.getUserInfo(db)?.get(0)?.goal_calorie
            val goalDistance =   mHealthFragmentModel.getUserInfo(db)?.get(0)?.goal_distance
            val goalHeartPont =  mHealthFragmentModel.getUserInfo(db)?.get(0)?.goal_heartpoint


            val caloriProgress = (totalBurnCalorie?.div(goalCalories!!))?.times(100)
            val stepProgress = (totalSteps?.div(goalSteps!!))?.times(100)
            val distanceProgress = (totalDistance?.div(goalDistance!!))?.times(100)
            val heartPointProgress = (totalHeartoint?.div(goalHeartPont!!))?.times(100)

            var leftSteps= goalSteps?.minus(totalSteps!!)!!.toInt()
            var leftCalorie= goalCalories?.minus(totalBurnCalorie!!)!!.toInt()
            var leftDistance= goalDistance?.minus(totalDistance!!)!!.toInt()
            var leftHeartPoint= goalHeartPont?.minus(totalHeartoint!!)!!.toInt()

            if (leftSteps<=0) leftSteps=0
            if (leftCalorie<=0) leftCalorie=0
            if (leftDistance<=0) leftDistance=0
            if (leftHeartPoint<=0) leftHeartPoint=0


            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            var distanceInKm= df.format((totalDistance!!/1000.0f))
            val dformat = DecimalFormat("#.##")
            //dformat.format()
            var leftDistanceInKm=dformat.format(((goalDistance!!/1000.0f)-df.format((totalDistance!!/1000.0f)).toFloat())).toString()
            if((leftDistanceInKm).toFloat()<=0)
                leftDistanceInKm="0"

            mHealthFragmentInterfaceViewInterface.setStepProgressGraph(stepProgress?.toFloat()!!,
                totalSteps!!.toInt(),leftSteps)
            mHealthFragmentInterfaceViewInterface.setCalorieProgressGraph(caloriProgress?.toFloat()!!,totalBurnCalorie!!.toInt(),leftCalorie)

            mHealthFragmentInterfaceViewInterface.setDistanceProgressGraph(distanceProgress?.toFloat()!!,distanceInKm,leftDistanceInKm)
            mHealthFragmentInterfaceViewInterface.setHeartPointProgressGraph(heartPointProgress?.toFloat()!!, totalHeartoint!!.toInt(),leftHeartPoint)

     //   }


    }




}