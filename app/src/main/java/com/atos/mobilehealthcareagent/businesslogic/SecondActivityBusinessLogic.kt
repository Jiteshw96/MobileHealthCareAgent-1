package com.atos.mobilehealthcareagent.businesslogic

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


class SecondActivityBusinessLogic {

    fun getTimeDifference(startTime:Long,endTime:Long):Boolean{



        val format = SimpleDateFormat("HH:mm:ss")
        val date1 = format.parse( getDate(endTime,"HH:mm:ss"))

        val date2 = format.parse( getDate(startTime,"HH:mm:ss"))

        Log.v("",""+(date1.time-date2.time))

        var deltaTime=((startTime-endTime)/100.0)/60.0

        if(deltaTime>5){
            return true
        }
        else{
            return false
        }


    }


    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @param dateFormat Date format
     * @return String representing date in specified format
     */
    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }
}