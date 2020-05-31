package com.atos.mobilehealthcareagent.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.*
import com.atos.mobilehealthcareagent.R
import com.atos.mobilehealthcareagent.contract.UserFitnessDatabaseInterface
import com.atos.mobilehealthcareagent.contract.UserFitnessDatabaseInterface.UserFitnessActivityViewInterface
import com.atos.mobilehealthcareagent.presenter.FitnesActivityPresenter
import com.atos.mobilehealthcareagent.service.ServiceInputToDB
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : Fragment(), UserFitnessActivityViewInterface {

    companion object {
        const val KEY_TASK_DESC = "key_task_desc"
    }
    lateinit var mFitnesActivityPresenter: FitnesActivityPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workmanager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mFitnesActivityPresenter=FitnesActivityPresenter(this)

    }


    override fun initLongRunningService() {
        val data = Data.Builder()
            .putString(KEY_TASK_DESC, "Hey "+ Calendar.getInstance().getTime().toString())
            .build()

        val constraints = Constraints.Builder()

            .build()

//        val request = PeriodicWorkRequest.Builder(ServiceInputToDB::class.java,20, TimeUnit.MINUTES)
//            .setInputData(data)
//            .setConstraints(constraints)
//            .build()

        val request =
            PeriodicWorkRequestBuilder<ServiceInputToDB>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()



        WorkManager.getInstance().enqueue(request)
    }
}
