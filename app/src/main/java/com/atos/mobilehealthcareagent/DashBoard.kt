package com.atos.mobilehealthcareagent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.*
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.fragments.HealthFragment
import com.atos.mobilehealthcareagent.fragments.ProfileFragment
import com.atos.mobilehealthcareagent.fragments.SecondFragment
import com.atos.mobilehealthcareagent.fragments.TrendsFragment
import com.atos.mobilehealthcareagent.googlefit.BackgroundTask
import com.atos.mobilehealthcareagent.service.ServiceInputToDB
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit


class DashBoard : AppCompatActivity(){

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
                       openFragment(TrendsFragment())
                    // open Profile Fragment
                    var task: BackgroundTask = BackgroundTask(applicationContext)

                    CoroutineScope(Dispatchers.IO).launch {
                        task.getFitnessData()
                    }

                    true
                }
                R.id.navigation_profile -> {
                    // Respond to navigation item 3 click
                      openFragment(ProfileFragment())
                    // open navigation fragment
                    true
                }
                else -> false
            }
        }
        initLongRunningService()


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

     fun initLongRunningService() {
        val data = Data.Builder()
            .putString(SecondFragment.KEY_TASK_DESC, "Hey "+ Calendar.getInstance().getTime().toString())
            .build()

        val constraints = Constraints.Builder()

            .build()

        val request = PeriodicWorkRequestBuilder<ServiceInputToDB>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag("mobilehealthcareagent")
            .build()


        WorkManager.getInstance().enqueueUniquePeriodicWork("MobileHealthCareAgent",
            ExistingPeriodicWorkPolicy.KEEP,request);
    }



}
