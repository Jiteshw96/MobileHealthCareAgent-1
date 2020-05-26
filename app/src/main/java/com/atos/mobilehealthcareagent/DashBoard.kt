package com.atos.mobilehealthcareagent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.User
import com.atos.mobilehealthcareagent.fragments.HealthFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field


class DashBoard : AppCompatActivity() {

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(HealthFragment())
        checkuserGoalCreatedOrNot()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_health -> {
                    // Respond to navigation item 1 click
                    openFragment(HealthFragment())
                    true
                }
                R.id.navigation_trends-> {
                    // Respond to navigation item 2 click
                    //   openFragment(HealthFragment())
                    // open Profile Fragment
                    true
                }
                R.id.navigation_profile -> {
                    // Respond to navigation item 3 click
                    //  openFragment(HealthFragment())
                    // open navigation fragment
                    true
                }
                else -> false
            }
        }


    }

    fun openFragment(fragment: Fragment?) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    fun checkuserGoalCreatedOrNot() {

        db = AppDatabase.getAppDatabase(applicationContext) as AppDatabase

        Log.e("Database Created", "Ready to Read/Write")


    }



}
