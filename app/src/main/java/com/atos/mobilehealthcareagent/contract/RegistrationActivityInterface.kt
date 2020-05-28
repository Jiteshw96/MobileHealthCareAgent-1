package com.atos.mobilehealthcareagent.contract

import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User


interface RegistrationActivityInterface {
    interface RegistrationActivityInterfaceViewInterface {
        fun init()
        fun getSDKPermission()
    }

    interface RegistrationActivityInterfacePresenterInterface {
        fun init()
        fun saveUser(user:User)


    }


    interface RegistrationActivityModelInterface {

        fun saveUserToDB(db: AppDatabase)


    }

}