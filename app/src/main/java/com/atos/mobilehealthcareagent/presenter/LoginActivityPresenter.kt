package com.atos.mobilehealthcareagent.presenter

import android.content.Context
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface.LoginActivityInterfaceViewInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal
import com.atos.mobilehealthcareagent.model.LoginModel

class LoginActivityPresenter : LoginActivityInterface.LoginActivityInterfacePresenterInterface {

    lateinit var mLoginActivityInterfaceViewInterface: LoginActivityInterfaceViewInterface

    lateinit var db: AppDatabase

    lateinit var context: Context

    lateinit var loginModel: LoginModel

    constructor(
        mLoginActivityInterfaceViewInterface: LoginActivityInterfaceViewInterface,
        context: Context
    ) {
        this.mLoginActivityInterfaceViewInterface = mLoginActivityInterfaceViewInterface
        this.context = context
        loginModel = LoginModel()
        init()
    }

    override fun init() {
        db = AppDatabase.getAppDatabase(context) as AppDatabase
        saveGoal()
    }

    override fun saveGoal() {

        loginModel.saveGoalToDB(db)

        checkUserAlradyRegisterOrNot()

    }

    override fun checkUserAlradyRegisterOrNot() {
        if (loginModel?.userRegisteredOrNot(db)!! > 0) {
            mLoginActivityInterfaceViewInterface.redirectToDashBoard()
        } else {
            mLoginActivityInterfaceViewInterface.initilizeContentView()
        }

    }
}