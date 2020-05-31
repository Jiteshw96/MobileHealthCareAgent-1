package com.atos.mobilehealthcareagent.model

import com.atos.mobilehealthcareagent.contract.HealthFragmentInterface
import com.atos.mobilehealthcareagent.contract.HealthFragmentInterface.HealthFragmentInterfaceModelInterface
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.database.UserFitnessData

class HealthFragmentModel : HealthFragmentInterfaceModelInterface {

    override fun getCalorieCount(startDate: Long?, endDate: Long?, db: AppDatabase): Double? {

        return  db?.userDao()?.getCalorieCount(startDate, endDate)
    }

    override fun getDistanceCount(startDate: Long?, endDate: Long?, db: AppDatabase): Double? {
        return db?.userDao()?.getDistanceCount(startDate, endDate)
    }

    override fun getFitnessDataCount(db: AppDatabase): List<UserFitnessData?>? {
        return db?.userDao()?.allFitnessData
    }

    override fun getHeartPointCount(startDate: Long?, endDate: Long?, db: AppDatabase): Double? {
        return db?.userDao()?.getHeartPointCount(startDate, endDate)
    }

    override fun getStepCount(startDate: Long?, endDate: Long?, db: AppDatabase): Double? {
        return db?.userDao()?.getStepCount(startDate, endDate)
    }

    override fun getUserInfo(db: AppDatabase): List<User?>? {
        return db?.userDao()?.all
    }


}