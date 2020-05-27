package com.atos.mobilehealthcareagent.service

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
//import com.atos.mobilehealthcareagent.TAG
import com.atos.mobilehealthcareagent.fragments.SecondFragment
import com.atos.mobilehealthcareagent.googlefit.BackgroundTask
import kotlinx.coroutines.*

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
            task.getFitnessData()
        }


        return Result.success(data1)
    }

}