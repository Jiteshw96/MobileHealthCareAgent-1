package com.atos.mobilehealthcareagent.businesslogic

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DashBoardBuissnessLogic {

    fun getTimeDifference(startTime: Long, endTime: Long): Boolean {


        val format = SimpleDateFormat("HH:mm:ss")
        val date1 = format.parse(getDate(endTime, "HH:mm:ss"))

        val date2 = format.parse(getDate(startTime, "HH:mm:ss"))

        Log.v("", "" + (date1.time - date2.time))

        var deltaTime = ((startTime - endTime) / 100.0) / 60.0

        if (deltaTime > 5) {
            return true
        } else {
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


    fun getToday(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return formatter.format(date)
    }

    fun getYesterday(): String {

        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)

        return formatter.format(date)
    }

    fun todayStartTimeEndTime():ArrayList<Long>{

        var returnValue=ArrayList<Long>()

        val myStartDate = getToday()+" 00:00:01"
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = sdf.parse(myStartDate)
        val startMilisecond = date.time
        returnValue.add(startMilisecond)

        val myEndDate = getToday()+" 23:59:59"

        val Enddate = sdf.parse(myEndDate)
        val endtMilisecond = Enddate.time

        returnValue.add(endtMilisecond)

        return returnValue

    }

    fun yesterdayStartTimeEndTime():ArrayList<Long>{

        var returnValue=ArrayList<Long>()

        val myStartDate = getYesterday()+" 00:00:01"
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = sdf.parse(myStartDate)
        val startMilisecond = date.time
        returnValue.add(startMilisecond)

        val myEndDate = getYesterday()+" 23:59:59"

        val Enddate = sdf.parse(myEndDate)
        val endtMilisecond = Enddate.time

        returnValue.add(endtMilisecond)

        return returnValue

    }
}