package com.atos.mobilehealthcareagent.contract

import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User

interface ProfileFragmentInterface {
    interface ProfileFragmentInterfaceViewInterface{

       // fun setDataFromDatabase()
        fun showUpdateToast(str:String)
        fun initialFeildsSetUp()
        fun onClickDoneEditUserProfile()

        fun setForm(mName:String,mHeight:String,mWeight:String,mGoal:String,mDob:String,mGender:Int)
    }

    interface ProfileFragmentInterfacePresenterInterface{

        fun saveUserData(mName:String,mheight:String,mWeight:String,mGoal:String,mDob:String,mGender:String)
        fun setFormData()
        fun validateInformation(mName:String,mheight:String,mWeight:String,mGoal:String,mDob:String,mGender:String):Boolean

    }

    interface ProfileFragmentInterfaceModelInterface{

        fun getUserData(db:AppDatabase):User?
        fun saveUserData(db:AppDatabase,user: User)
    }

}