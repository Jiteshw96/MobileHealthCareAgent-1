package com.atos.mobilehealthcareagent.model

import com.atos.mobilehealthcareagent.contract.ProfileFragmentInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User

class ProfileFragmentModel:ProfileFragmentInterface.ProfileFragmentInterfaceModelInterface {
    override fun getUserData(db: AppDatabase): User? {

        return db.userDao()?.all?.get(0)
    }

    override fun saveUserData(db: AppDatabase,user: User) {

        db.userDao()?.insertAll(user)
    }


}