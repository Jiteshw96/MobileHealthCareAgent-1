
package com.atos.mobilehealthcareagent.presenter

import android.content.Context
import android.widget.Toast
import com.atos.mobilehealthcareagent.contract.ProfileFragmentInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.model.ProfileFragmentModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragmentPresenter : ProfileFragmentInterface.ProfileFragmentInterfacePresenterInterface {

    lateinit var db: AppDatabase
     var context: Context
     var mProfileFragmentInterfaceViewInterface: ProfileFragmentInterface.ProfileFragmentInterfaceViewInterface
     var mProfilefragmentModel: ProfileFragmentModel

    constructor(
        mProfileFragmentInterfaceViewInterface: ProfileFragmentInterface.ProfileFragmentInterfaceViewInterface,
        context: Context
    ) {

        this.mProfilefragmentModel = ProfileFragmentModel()
        this.mProfileFragmentInterfaceViewInterface = mProfileFragmentInterfaceViewInterface
        this.context = context
        init()
    }

    fun init() {

        db = AppDatabase.getAppDatabase(context) as AppDatabase
        setFormData()
    }

    override fun setFormData() {
        var user = mProfilefragmentModel.getUserData(db)
        val name = user?.firstName
        val gender: Int = if (user?.gender == "Male") {
            0
        } else if (user?.gender == "Female") {
            1
        } else {
            2
        }
        var dob = user?.dob
        var height = user?.height!!.toString()
        var weight = user?.weight!!.toString()
        var goal = user?.goal_steps!!.toString()

        this.mProfileFragmentInterfaceViewInterface.setForm(name!!, height, weight, goal, dob!!, gender)

    }

    override fun validateInformation(mName:String,mheight:String,mWeight:String,mGoal:String,mDob:String,mGender:String):Boolean {

        if (mName.length > 0 &&
            mDob.length > 0 &&
            mheight.length > 0 &&
            mWeight.length > 0 &&
            mGoal.length > 0
        ){
            return true
        }

        return false
    }

    override fun saveUserData(
        mName: String,
        mHeight: String,
        mWeight: String,
        mGoal: String,
        mDob: String,
        mGender: String
    ) {

        var user = User()
        if (mName.trim().length > 0) {
            user.firstName = mName.trim()
        }

        if(mGender.length > 0){
            user.gender = mGender
        }

        if (mDob.length > 0) {
            user.dob = mDob.trim()
        }
        if (mHeight.length > 0) {
            user.height = mHeight.toInt()
        }
        if (mWeight.length > 0) {
            user.weight = mWeight.toInt()
        }
        if (mGoal.length > 0) {
            user.goal_steps = mGoal.toLong()
        }

        if (mName.length > 0 &&
            mDob.length > 0 &&
            mHeight.length > 0 &&
            mWeight.length > 0 &&
            mGoal.length > 0
        ) {

            var goal = db.userDao()?.getGoalValue((mGoal.toLong()))

            user.goal_calorie = goal?.calorie!!
            user.goal_steps = goal?.steps!!
            user.goal_distance = goal?.distance!!
            user.goal_heartpoint = goal?.heartpoint!!
            user.goal_moveminute = goal?.moveminute!!

            mProfilefragmentModel.saveUserData(db,user)

        }

    }

}
