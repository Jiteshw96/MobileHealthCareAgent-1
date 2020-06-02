package com.atos.mobilehealthcareagent.businesslogic

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrendsBusinessLogic{


    //Get Time for Today
    fun getToday(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return formatter.format(date)
    }

    fun todayStartTimeEndTime(): ArrayList<Long> {
        var returnValue = ArrayList<Long>()
        val myStartDate = getToday() + " 00:00:01"
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val date = sdf.parse(myStartDate)
        val startMilisecond = date.time
        returnValue.add(startMilisecond)
        val myEndDate = getToday() + " 23:59:59"
        val Enddate = sdf.parse(myEndDate)
        val endtMilisecond = Enddate.time
        returnValue.add(endtMilisecond)
        return returnValue
    }

    fun getYesterday(): String {

        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)

        return formatter.format(date)
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