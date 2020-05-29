package com.atos.mobilehealthcareagent.contract

import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.database.UserFitnessData


interface HealthFragmentInterface {
    interface HealthFragmentInterfaceViewInterface {
        fun initView()


        fun todayOperation()

        fun yestardayIperation()


        fun setCalorieProgressGraph(value: Float, toInt: Int)
        fun setStepProgressGraph(value: Float, toInt: Int)
        fun setDistanceProgressGraph(value: Float, toInt: Int)
        fun setHeartPointProgressGraph(value: Float, toInt: Int)

    }

    interface HealthFragmentInterfacePresenterInterface {
        fun initialize()
        fun getUserInfo(db:AppDatabase): User?
        fun getProgressGraph(list:ArrayList<Long>)

    }

    interface HealthFragmentInterfaceModelInterface {
        fun getUserInfo(db:AppDatabase): List<User?>?
        fun getFitnessDataCount(db:AppDatabase):List<UserFitnessData?>?
        fun getCalorieCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?
        fun getStepCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?
        fun getDistanceCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?
        fun getHeartPointCount(startDate: Long?,endDate: Long?,db:AppDatabase): Double?


    }


}