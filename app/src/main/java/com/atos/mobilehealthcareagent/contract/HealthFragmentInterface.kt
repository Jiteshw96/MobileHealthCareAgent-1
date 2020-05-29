package com.atos.mobilehealthcareagent.contract

import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.database.UserFitnessData


interface HealthFragmentInterface {
    interface DashBoardActivityInterfaceViewInterface {
        fun initView()


        fun todayOperation()

        fun yestardayIperation()

        fun fetchdata()

    }

    interface DashBoardActivityInterfacePresenterInterface {
        fun initialize()

    }

    interface DashBoardActivityInterfaceModelInterface {
        fun getUserInfo(db:AppDatabase): User?
        fun getFitnessDataCount(db:AppDatabase):List<UserFitnessData?>?
        fun getCalorieCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?
        fun getStepCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?
        fun getDistanceCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?
        fun getHeartPointCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?


    }


}