package com.atos.mobilehealthcareagent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.atos.mobilehealthcareagent.fragments.HealthFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class Login : AppCompatActivity() {
    var bottomNavigation: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFragment(HealthFragment())

        bottom_navigation.setOnNavigationItemReselectedListener {  navigationItemSelectedListener }
    }

    fun openFragment(fragment: Fragment?) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    var navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    openFragment(HealthFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_sms -> {
                    openFragment(HealthFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    openFragment(HealthFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
