package com.atos.mobilehealthcareagent.contract

import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User


interface UserBasicDatabaseInterface {
    interface UserBasicDataBaseViewInterface {
        fun initUserFitnessDatabase()
        suspend fun userBasicInfoPresentIntoDB(flag: Boolean)

    }

    interface UserBasicDataPresenterInterface {
        fun buttonClickAddBasecDataToDatabase(db: AppDatabase, user: User)
        fun getInfoBasicDataPresentOrNotIntoDB(db: AppDatabase)
    }


    interface UserBasicDataModelInterface {
        suspend fun getUserBasicData(db: AppDatabase): User?
        suspend fun addUserBasicData(db: AppDatabase, user: User): LongArray?
    }

}