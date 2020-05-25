package com.atos.mobilehealthcareagent.presenter


import android.util.Log
import com.atos.mobilehealthcareagent.contract.UserBasicDatabaseInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.model.PersonalDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PersonalDataPresenter :
    UserBasicDatabaseInterface.UserBasicDataPresenterInterface {

    lateinit var mPersonalDataModel: PersonalDataModel
    lateinit var mViewDatabase: UserBasicDatabaseInterface.UserBasicDataBaseViewInterface

    constructor(
        mViewDatabase: UserBasicDatabaseInterface.UserBasicDataBaseViewInterface
    ) {

        this.mViewDatabase = mViewDatabase
        initPresenter()
    }

    private fun initPresenter() {
        mPersonalDataModel = PersonalDataModel()

        mViewDatabase.initUserFitnessDatabase()
    }


    override fun buttonClickAddBasecDataToDatabase(db: AppDatabase, user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            var value = mPersonalDataModel.addUserBasicData(db, user)
            Log.e("PersonalValueInserted", value.toString())
            mViewDatabase.userBasicInfoPresentIntoDB(true)
        }

    }

    override fun getInfoBasicDataPresentOrNotIntoDB(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            var value = mPersonalDataModel.getUserBasicData(db)
            if (value != null)
                mViewDatabase.userBasicInfoPresentIntoDB(true)
            else
                mViewDatabase.userBasicInfoPresentIntoDB(false)
        }

    }


}