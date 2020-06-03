package com.atos.mobilehealthcareagent

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.work.*
import com.atos.mobilehealthcareagent.contract.DashBoardActivityInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.fragments.*
import com.atos.mobilehealthcareagent.googlefit.BackgroundTask
import com.atos.mobilehealthcareagent.presenter.DashBoardActivityPresenter
import com.atos.mobilehealthcareagent.service.ServiceInputToDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class DashBoard : AppCompatActivity(),
    DashBoardActivityInterface.DashBoardActivityInterfaceViewInterface ,
    DatePickerDialog.OnDateSetListener {

    lateinit var db: AppDatabase
    lateinit var mDashBoardActivityPresenter: DashBoardActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDashBoardActivityPresenter=DashBoardActivityPresenter(this,this)
    }

    override fun openFragment(fragment: Fragment?) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun checkuserGoalCreatedOrNot() {

        db = AppDatabase.getAppDatabase(applicationContext) as AppDatabase

        Log.e("Database Created", "Ready to Read/Write")


    }

    override fun onBackPressed() {
        finish()
    }

     override fun initLongRunningService() {
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


    companion object var openFragment=0
    override fun initialize(openFragment:Int,today:Boolean){

        this.openFragment=openFragment
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_health -> {
                    // Respond to navigation item 1 click
                    openFragment(HealthFragment())
                    true
                }
                R.id.navigation_trends-> {
                    // Respond to navigation item 2 click
                   // openFragment(TrendsFragment())
                    openTrendFragment(openFragment,today)
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

    }


    open fun openTrendFragment(value:Int,today:Boolean){
        when(value){
            0->openFragment(TrendsFragment(today))
            1->openFragment(CaloriesTrendFragment(today))
            2->openFragment(DistanceTrendFragment(today))
            3->openFragment(HeartPointTrendFragment(today))
           // 4->openFragment()
        }

    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {

        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = day

        val dob = DateFormat.getDateInstance().format(c.time)
        val dobText = findViewById<View>(R.id.dob) as EditText
        dobText.setText(dob)
    }


}
