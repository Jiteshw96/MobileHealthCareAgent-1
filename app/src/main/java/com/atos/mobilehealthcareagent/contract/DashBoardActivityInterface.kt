package com.atos.mobilehealthcareagent.contract

import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User


interface DashBoardActivityInterface {
    interface DashBoardActivityInterfaceViewInterface {
        fun initialize()
        fun openFragment(fragment: Fragment?)

        fun checkuserGoalCreatedOrNot()

        fun initLongRunningService()

    }

    interface DashBoardActivityInterfacePresenterInterface {
        fun initialize()

    }



}