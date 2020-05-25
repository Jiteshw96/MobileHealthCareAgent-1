package com.atos.mobilehealthcareagent.model

import android.util.Log
import com.atos.mobilehealthcareagent.contract.UserBasicDatabaseInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class PersonalDataModel :
    UserBasicDatabaseInterface.UserBasicDataModelInterface {

    override suspend fun addUserBasicData(db: AppDatabase, user: User): LongArray? {
        var indexNumber_user_basic_info_DB: LongArray? = null
        indexNumber_user_basic_info_DB = db.userDao()?.insertAll(user)
        Log.e("Value", indexNumber_user_basic_info_DB.toString())

        Log.e("Value", db.userDao()?.countUsers().toString())
        return indexNumber_user_basic_info_DB
    }

    override suspend fun getUserBasicData(db: AppDatabase): User? {

        //  return ArrayList<User>()

        val userList = db.userDao()?.all
        var user: User? = null

        when (userList?.size) {
            1 -> {
                user = userList?.get(0)
            }
            0 -> {
                user = null
            }
        }
        return user
    }

}