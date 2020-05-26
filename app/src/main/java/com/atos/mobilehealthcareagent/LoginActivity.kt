package com.atos.mobilehealthcareagent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal
import com.atos.mobilehealthcareagent.fragments.HealthFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field


class LoginActivity : AppCompatActivity() {
    lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkuserGoalCreatedOrNot()

    }






     fun checkuserGoalCreatedOrNot() {

        db = AppDatabase.getAppDatabase(applicationContext) as AppDatabase

        Log.e("Database Created", "Ready to Read/Write")
         saveGoal()

    }

    fun saveGoal(){

        if(db?.userDao()?.allGoal?.size==0) {


            var goalvalue5 = Goal()
            goalvalue5.steps = 5000
            goalvalue5.calorie = 4000
            goalvalue5.distance = 4000
            goalvalue5.heartpoint = 15
            goalvalue5.moveminute = 100

            db?.userDao()?.insertAllGoal(goalvalue5)

            var goalvalue6 = Goal()
            goalvalue6.steps = 6000
            goalvalue6.calorie = 4800
            goalvalue6.distance = 4800
            goalvalue6.heartpoint = 20
            goalvalue6.moveminute = 120

            db?.userDao()?.insertAllGoal(goalvalue6)

            var goalvalue7 = Goal()
            goalvalue7.steps = 7000
            goalvalue7.calorie = 5600
            goalvalue7.distance = 5600
            goalvalue7.heartpoint = 25
            goalvalue7.moveminute = 140

            db?.userDao()?.insertAllGoal(goalvalue7)



        }


    }

}
