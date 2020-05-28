package com.atos.mobilehealthcareagent.model

import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal

class LoginModel : LoginActivityInterface.LoginActivityModelInterface {

    override fun saveGoalToDB(db: AppDatabase) {
        if (db?.userDao()?.allGoal?.size == 0) {


            var goalvalue5 = Goal()
            goalvalue5.steps = 5000
            goalvalue5.calorie = 4000
            goalvalue5.distance = 4000
            goalvalue5.heartpoint = 15
            goalvalue5.moveminute = 100

            db?.userDao()?.insertAllGoal(goalvalue5)

            var goalvalue6 = Goal()
            goalvalue6.steps = 6000
            goalvalue6.calorie = 4800
            goalvalue6.distance = 4800
            goalvalue6.heartpoint = 20
            goalvalue6.moveminute = 120

            db?.userDao()?.insertAllGoal(goalvalue6)

            var goalvalue7 = Goal()
            goalvalue7.steps = 7000
            goalvalue7.calorie = 5600
            goalvalue7.distance = 5600
            goalvalue7.heartpoint = 25
            goalvalue7.moveminute = 140

            db?.userDao()?.insertAllGoal(goalvalue7)


        }
    }

    override fun userRegisteredOrNot(db: AppDatabase): Int? {
        return db?.userDao()?.all?.size
    }
}