package com.atos.mobilehealthcareagent.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.businesslogic.DashBoardBuissnessLogic
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.generated.callback.OnClickListener
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.dashboard.*


class HealthFragment : Fragment() {

    var today=true

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        previos_button.setOnClickListener {
           yestardayIperation()
        }
        next_button.setOnClickListener {
          todayOperation()
        }

    }


    fun initView(){
        db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase

        Log.e("Database Created", "Ready to Read/Write")
      todayOperation()
    }


    fun todayOperation(){
        today=true
        previos_button.isClickable=true
        next_button.isClickable=false
        txt_today_tomorrow_date.text= DashBoardBuissnessLogic().getToday()
        txt_today_tomorrow.text= getString(R.string.today);
        fetchdata(DashBoardBuissnessLogic().todayStartTimeEndTime())

    }
    fun yestardayIperation(){
        today=false
        previos_button.isClickable=false
        next_button.isClickable=true
        txt_today_tomorrow_date.text= DashBoardBuissnessLogic().getYesterday()
        txt_today_tomorrow.text= getString(R.string.yesterday);
        fetchdata(DashBoardBuissnessLogic().yesterdayStartTimeEndTime())
    }

    fun  fetchdata(list:ArrayList<Long>){

        Log.v(""," "+list.get(1))
        var totalBurnCalorie=db?.userDao()?.getCalorieCount(list.get(0),list.get(1))
        var totalSteps=db?.userDao()?.getStepCount(list.get(0),list.get(1))
        var totalDistance=db?.userDao()?.getDistanceCount(list.get(0),list.get(1))
        var totalHeartoint=db?.userDao()?.getHeartPointCount(list.get(0),list.get(1))

        Log.v("totalBurnCalorie",""+totalBurnCalorie);
        Log.v("totalSteps",""+totalSteps);
        Log.v("totalDistance",""+totalDistance);
        Log.v("totalHeartoint",""+totalHeartoint);

        val goalSteps = db?.userDao()?.all?.get(0)?.goal_steps
        val goalCalories = db?.userDao()?.all?.get(0)?.goal_calorie
        val goalDistance = db?.userDao()?.all?.get(0)?.goal_distance
        val goalHeartPont = db?.userDao()?.all?.get(0)?.goal_heartpoint


        val caloriProgress = (totalBurnCalorie?.div(goalCalories!!))?.times(100)
        val stepProgress = (totalSteps?.div(goalSteps!!))?.times(100)
        val distanceProgress = (totalDistance?.div(goalDistance!!))?.times(100)
        val heartPointProgress = (totalHeartoint?.div(goalHeartPont!!))?.times(100)


        steps_progress_bar.progress = stepProgress?.toFloat()!!
        calorie_progress_bar.progress= caloriProgress?.toFloat()!!
        distance_progress_bar.progress = distanceProgress?.toFloat()!!
        heart_progress_bar.progress = heartPointProgress?.toFloat()!!

    }


}
