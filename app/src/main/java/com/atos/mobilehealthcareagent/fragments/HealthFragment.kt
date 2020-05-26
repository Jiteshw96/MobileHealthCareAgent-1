package com.atos.mobilehealthcareagent.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.businesslogic.DashBoardBuissnessLogic
import com.atos.mobilehealthcareagent.generated.callback.OnClickListener
import kotlinx.android.synthetic.main.dashboard.*


class HealthFragment : Fragment() {

    var today=true

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
    }

}
