package com.atos.mobilehealthcareagent.presenter

import com.atos.mobilehealthcareagent.contract.UserFitnessDatabaseInterface
import com.atos.mobilehealthcareagent.contract.UserFitnessDatabaseInterface.UserFitnessActivityViewInterface

class FitnesActivityPresenter: UserFitnessDatabaseInterface.UserFitnessActivityPresenterInterface {

    lateinit var mUserFitnessActivityViewInterface: UserFitnessActivityViewInterface

    constructor(mUserFitnessActivityViewInterface: UserFitnessActivityViewInterface){
        this.mUserFitnessActivityViewInterface=mUserFitnessActivityViewInterface
        init()

    }

    fun init(){
        this.mUserFitnessActivityViewInterface.initLongRunningService()
    }
}