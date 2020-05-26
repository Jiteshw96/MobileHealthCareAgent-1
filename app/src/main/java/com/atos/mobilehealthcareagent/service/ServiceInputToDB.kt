package com.atos.mobilehealthcareagent.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.atos.mobilehealthcareagent.R
//import com.atos.mobilehealthcareagent.TAG
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.UserFitnessData
import com.atos.mobilehealthcareagent.fitnessharedpreferences.LastSyncSharedPreferences
import com.atos.mobilehealthcareagent.fragments.SecondFragment
import com.atos.mobilehealthcareagent.googlefit.BackgroundTask
import com.atos.mobilehealthcareagent.googlefit.GetDateDetailsStartEndTime
import com.atos.mobilehealthcareagent.googlefit.getEndTimeString
import com.atos.mobilehealthcareagent.googlefit.getStartTimeString
import com.atos.mobilehealthcareagent.googlefit.readfitnessapi.ReadFitDataApi
import com.atos.mobilehealthcareagent.presenter.FitnesActivityPresenter
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.data.Field.FIELD_STEPS
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class ServiceInputToDB(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {




    // var mStartDate = "2020-05-11" + " 11:40:01"
   // var mEndDate = "2020-05-19" + " 12:51:59"

    companion object {
        const val KEY_TASK_OUTPUT = "key_task_output"
    }

    override fun doWork(): Result {

        val data = inputData
        val desc = data.getString(SecondFragment.KEY_TASK_DESC)
        val data1 = Data.Builder()
            .putString(KEY_TASK_OUTPUT, "Task Finished Successfully")
            .build()

        var task:BackgroundTask = BackgroundTask(applicationContext)

        CoroutineScope(Dispatchers.IO).launch {
            task.getData()
        }


        return Result.success(data1)
    }

}