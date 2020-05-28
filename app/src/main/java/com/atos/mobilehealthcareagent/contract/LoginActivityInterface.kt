package com.atos.mobilehealthcareagent.contract

import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User


interface LoginActivityInterface {
    interface LoginActivityInterfaceViewInterface {
        fun init()

        fun redirectToDashBoard()

        fun initilizeContentView()

    }

    interface LoginActivityInterfacePresenterInterface {
        fun init()
        fun saveGoal()
        fun checkUserAlradyRegisterOrNot()
    }


    interface LoginActivityModelInterface {

        fun saveGoalToDB(db: AppDatabase)
        fun userRegisteredOrNot(db: AppDatabase): Int?

    }

}