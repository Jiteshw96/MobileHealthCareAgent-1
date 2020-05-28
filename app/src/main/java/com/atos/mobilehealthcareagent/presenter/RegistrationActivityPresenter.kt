package com.atos.mobilehealthcareagent.presenter

import android.content.Context
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface.LoginActivityInterfaceViewInterface
import com.atos.mobilehealthcareagent.contract.RegistrationActivityInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.model.LoginModel

class RegistrationActivityPresenter :
    RegistrationActivityInterface.RegistrationActivityInterfacePresenterInterface {

    lateinit var mRegistrationActivityInterfaceViewInterface: RegistrationActivityInterface.RegistrationActivityInterfaceViewInterface

    lateinit var context:Context

    lateinit var db: AppDatabase

    constructor(mRegistrationActivityInterfaceViewInterface: RegistrationActivityInterface.RegistrationActivityInterfaceViewInterface,
                  context:Context  ){
        this.mRegistrationActivityInterfaceViewInterface=mRegistrationActivityInterfaceViewInterface
        this.context=context
        init()
    }


    override fun init() {
        db = AppDatabase.getAppDatabase(context) as AppDatabase
        mRegistrationActivityInterfaceViewInterface.init()
    }

    override fun saveUser(user: User) {
        db.userDao()?.insertAll(user)
        mRegistrationActivityInterfaceViewInterface.getSDKPermission()

    }

}