package com.atos.mobilehealthcareagent.fragments

import android.os.Bundle
import android.content.Intent
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.SetGoalsActivity
import com.atos.mobilehealthcareagent.businesslogic.MinMaxFilter
import com.atos.mobilehealthcareagent.contract.ProfileFragmentInterface
import com.atos.mobilehealthcareagent.database.AppDatabase


import com.atos.mobilehealthcareagent.fitnessharedpreferences.GoalSharedPreferences
import com.atos.mobilehealthcareagent.presenter.ProfileFragmentPresenter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.dob
import kotlinx.android.synthetic.main.fragment_profile.gender_spinner
import kotlinx.android.synthetic.main.fragment_profile.goal
import kotlinx.android.synthetic.main.fragment_profile.height
import kotlinx.android.synthetic.main.fragment_profile.name_value
import kotlinx.android.synthetic.main.fragment_profile.weight
import java.text.DateFormat
import java.util.*



class ProfileFragment : Fragment() , View.OnTouchListener,View.OnClickListener,ProfileFragmentInterface.ProfileFragmentInterfaceViewInterface {

    lateinit var db: AppDatabase
    lateinit var profileFragmentPresenter: ProfileFragmentPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialFeildsSetUp()
        profileFragmentPresenter = ProfileFragmentPresenter(this, this.context!!)

    }

    override fun initialFeildsSetUp() {
        this.height.filters = arrayOf<InputFilter>(MinMaxFilter(1, 229))
        this.weight.filters = arrayOf<InputFilter>(MinMaxFilter(1, 150))
        this.goal.filters = arrayOf<InputFilter>(MinMaxFilter(1, 20000))

        var list = ArrayList<String>()
        list.add("Male")
        list.add("Female")
        list.add("Other")
        list.add("Select Gender")


        val dataAdapter: ArrayAdapter<String?> = object :
            ArrayAdapter<String?>(this.context!!, R.layout.custom_spinner, list as List<String?>) {
            override fun getCount(): Int {
                return list.size - 1// Truncate the list
            }
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.gender_spinner.adapter = dataAdapter
        this.gender_spinner.setSelection(list.size - 1)

        this.dob.setOnTouchListener(this)
        this.goal.setOnTouchListener(this)

        this.done_btn.setOnClickListener {
            onClickDoneEditUserProfile()
        }

    }

    override fun onClick(v: View?) {
        Log.v("", "ssd")
    }

    override fun onTouch(
        view: View?,
        motionEvent: MotionEvent?
    ): Boolean {

        when (view) {

            dob -> {
                if (motionEvent?.action == MotionEvent.ACTION_DOWN) {
                    val datePicker: DialogFragment = DatePickerFragment()
                    datePicker.show(activity!!.supportFragmentManager, "date picker")
                }
            }

            goal -> {

                if (motionEvent?.action == MotionEvent.ACTION_DOWN) {
                    var intent = Intent(this.context, SetGoalsActivity::class.java)
                    intent.putExtra("name", name_value.text.toString())
                    intent.putExtra("gender", gender_spinner.selectedItemPosition)
                    intent.putExtra("dob", dob.text.toString())
                    intent.putExtra("height", height.text.toString())
                    intent.putExtra("weight", weight.text.toString())
                    startActivity(intent)
                }

            }
        }
        return true
    }


    /*override fun setDataFromDatabase() {
        *//*  db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase
       name_value.setText(db.userDao()?.all?.get(0)?.firstName)
       if(db.userDao()?.all?.get(0)?.gender == "Male"){
           gender_spinner.setSelection(0)
       }else if (db.userDao()?.all?.get(0)?.gender == "Female"){
           gender_spinner.setSelection(1)
       }else{
           gender_spinner.setSelection(2)
       }
       dob.setText(db.userDao()?.all?.get(0)?.dob)
       height.setText(db.userDao()?.all?.get(0)?.height!!.toString())
       weight.setText(db.userDao()?.all?.get(0)?.weight!!.toString())
       goal.setText(db.userDao()?.all?.get(0)?.goal_steps !!.toString())*//*
    }*/

    override fun setForm(
        mName: String,
        mHeight: String,
        mWeight: String,
        mGoal: String,
        mDob: String,
        mGender: Int
    ) {
        height.setText(mHeight)
        weight.setText(mWeight)
        goal.setText(mGoal)
        dob.setText(mDob)
        gender_spinner.setSelection(mGender)
        name_value.setText(mName)
    }


    override fun onClickDoneEditUserProfile() {

        val name = name_value.text.trim().toString()
        val gender = gender_spinner.selectedItem.toString()
        val dob = this.dob.text.trim().toString()
        val height = this.height.text.trim().toString()
        val weight = this.weight.text.trim().toString()
        val goal = this.goal.text.trim().toString()

       if (profileFragmentPresenter.validateInformation(name,height,weight,goal,dob,gender)){

           profileFragmentPresenter.saveUserData(name,height,weight,goal,dob,gender)
           showUpdateToast("User data updated")
       }else{
           showUpdateToast("Enter all values")
       }



    }

    override fun showUpdateToast(str:String) {
        Toast.makeText(activity, str, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
            super.onResume()
            if (GoalSharedPreferences().getGoal(activity!!.applicationContext)?.length!! > 0) {

                goal.setText(GoalSharedPreferences().getGoal(activity!!.applicationContext))

                GoalSharedPreferences().removeGoal(activity!!.applicationContext)
            }
        }
    }





/*
val date1 = Calendar.getInstance()
 val builder = MaterialDatePicker.Builder.datePicker()
 builder.setSelection(date1.timeInMillis)
 val now = Calendar.getInstance()

 val picker = builder.build()
 picker.show(activity?.supportFragmentManager!!, picker.toString())

// picker.addOnNegativeButtonClickListener { dismiss() }
// picker.addOnPositiveButtonClickListener { Log.i("","The selected date range is ${it.first} - ${it.second}")}

 picker.addOnPositiveButtonClickListener {
     Log.i("","The selected date range is ${it}")
 }
 */






