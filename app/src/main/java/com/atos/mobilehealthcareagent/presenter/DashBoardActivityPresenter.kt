package com.atos.mobilehealthcareagent.presenter

import android.content.Context
import com.atos.mobilehealthcareagent.contract.DashBoardActivityInterface
import com.atos.mobilehealthcareagent.contract.DashBoardActivityInterface.DashBoardActivityInterfaceViewInterface
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface.LoginActivityInterfaceViewInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal
import com.atos.mobilehealthcareagent.fragments.HealthFragment
import com.atos.mobilehealthcareagent.model.LoginModel

class DashBoardActivityPresenter :
    DashBoardActivityInterface.DashBoardActivityInterfacePresenterInterface {

    lateinit var mDashBoardActivityInterfaceViewInterface: DashBoardActivityInterfaceViewInterface


    constructor(
        mDashBoardActivityInterfaceViewInterface: DashBoardActivityInterfaceViewInterface,
        context: Context
    ) {

        this.mDashBoardActivityInterfaceViewInterface=mDashBoardActivityInterfaceViewInterface
        initialize()
    }

    override fun initialize() {
        mDashBoardActivityInterfaceViewInterface.initialize()
        mDashBoardActivityInterfaceViewInterface.openFragment(HealthFragment())
        mDashBoardActivityInterfaceViewInterface.checkuserGoalCreatedOrNot()
        mDashBoardActivityInterfaceViewInterface.initLongRunningService()
    }

}