package com.atos.mobilehealthcareagent.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.text.InputFilter
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
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.presenter.RegistrationActivityPresenter
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.dob
import kotlinx.android.synthetic.main.fragment_profile.gender_spinner
import kotlinx.android.synthetic.main.fragment_profile.goal
import kotlinx.android.synthetic.main.fragment_profile.height
import kotlinx.android.synthetic.main.fragment_profile.name_value
import kotlinx.android.synthetic.main.fragment_profile.weight
import java.util.ArrayList


class ProfileFragment : Fragment() , View.OnTouchListener {

    lateinit var db: AppDatabase
    lateinit var mRegistrationActivityPresenter: RegistrationActivityPresenter

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
        height.filters = arrayOf<InputFilter>(MinMaxFilter(1, 229))
        weight.filters = arrayOf<InputFilter>(MinMaxFilter(1, 150))
        goal.filters = arrayOf<InputFilter>(MinMaxFilter(1, 20000))

        var list = ArrayList<String>()
        list.add("Male")
        list.add("Female")
        list.add("Other")
        list.add("Select Gender")


        val dataAdapter: ArrayAdapter<String?> = object :
            ArrayAdapter<String?>(this.context!!, R.layout.custom_spinner, list as List<String?>) {
            override fun getCount(): Int {
                return  list.size-1// Truncate the list
            }
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        gender_spinner.adapter = dataAdapter
        gender_spinner.setSelection(list.size-1)

        dob.setOnTouchListener(this)
        goal.setOnTouchListener(this)
        setDataFromDatabase()

    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean { //To change body of created functions use File | Settings | File Templates.

        when(view){

            dob -> {
                if(motionEvent?.action == MotionEvent.ACTION_DOWN){
                    val datePicker: DialogFragment = DatePickerFragment()
                    datePicker.show(activity!!.supportFragmentManager, "date picker")
                }
            }

            goal-> {

                if(motionEvent?.action == MotionEvent.ACTION_DOWN){
                    var intent = Intent(this.context,SetGoalsActivity::class.java)
                    intent.putExtra("name",name_value.text.toString())
                    intent.putExtra("gender",gender_spinner.selectedItemPosition)
                    intent.putExtra("dob",dob.text.toString())
                    intent.putExtra("height",height.text.toString())
                    intent.putExtra("weight",weight.text.toString())
                    startActivity(intent)
                }

            }
        }
        return true
    }


   fun setDataFromDatabase(){
        db = AppDatabase.getAppDatabase(activity!!.applicationContext) as AppDatabase

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
       goal.setText(db.userDao()?.all?.get(0)?.goal_steps !!.toString())
    }

    fun onclickDoneRegistration(view:View){

        var user = User()
        if (name_value.text.trim().length > 0) {
            user.firstName = name_value.text.trim().toString()
        }
        else{
            Toast.makeText(this.context,"Enter Name",Toast.LENGTH_LONG).show()
        }

        user.gender = gender_spinner.selectedItem.toString()

        if (dob.text.trim().length > 0) {
            user.dob = dob.text.trim().toString()
        }
        else{
            Toast.makeText(this.context,"Select date of birth",Toast.LENGTH_LONG).show()
        }
        if (height.text.trim().length > 0) {
            user.height = height.text.trim().toString().toInt()
        }
        else{
            Toast.makeText(this.context,"Enter height",Toast.LENGTH_LONG).show()
        }
        if (weight.text.trim().length > 0) {
            user.weight = weight.text.trim().toString().toInt()
        }
        else{
            Toast.makeText(this.context,"Enter weight",Toast.LENGTH_LONG).show()
        }
        if (goal.text.trim().length > 0) {
            user.goal_steps = goal.text.trim().toString().toLong()
        }
        else{
            Toast.makeText(this.context,"Enter goal",Toast.LENGTH_LONG).show()
        }

        if(name_value.text.trim().length > 0 &&
            dob.text.trim().length > 0 &&
            height.text.trim().length > 0 &&
            weight.text.trim().length > 0 &&
            goal.text.trim().length > 0  ){

            db?.userDao()?.getGoalValue(7000)

            mRegistrationActivityPresenter.saveUser(user,(goal.text.trim().toString()).toLong())
        }
        else{
            Toast.makeText(this.context,"Enter all values",Toast.LENGTH_LONG).show()
        }
    }


}





