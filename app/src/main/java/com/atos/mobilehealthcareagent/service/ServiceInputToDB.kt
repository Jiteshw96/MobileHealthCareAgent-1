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
import com.atos.mobilehealthcareagent.fragments.SecondFragment
import java.util.*

class ServiceInputToDB(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    var NotificationChannelID="mobilehealthcareagent"
    var NotificationChannelName="mobilehealthcareagent"
    var NotificationContentTitle="Mobile HealthCare Agent"
    override fun doWork(): Result {
        val data = inputData
        val desc = data.getString(SecondFragment.KEY_TASK_DESC)
        displayNotification(NotificationContentTitle, desc)
        val data1 = Data.Builder()
            .putString(KEY_TASK_OUTPUT, "Task Finished Successfully")
            .build()


        return Result.success(data1)
    }

    private fun displayNotification(task: String, desc: String?) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationChannelID,
                NotificationChannelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val builder =
            NotificationCompat.Builder(applicationContext, NotificationChannelID)
                .setContentTitle(task)
                .setContentText(Calendar.getInstance().getTime().toString())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
        manager.notify(1, builder.build())
        Log.e("Time", Calendar.getInstance().getTime().toString())
    }

    companion object {
        const val KEY_TASK_OUTPUT = "key_task_output"
    }

}