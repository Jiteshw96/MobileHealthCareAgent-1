package com.atos.mobilehealthcareagent.googlefit

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GetDateDetailsStartEndTime {


    public fun ListOfDays(days:Int):ArrayList<DateStartEnd>{

        return getCalculatedDate("","yyyy-MM-dd", days)


    }

    public fun ListOfDaysForGraph(days:Int): ArrayList<DateStartEndForGraph> {

        return getCalculatedDateForGraph("","yyyy-MM-dd", days)


    }


    public fun ListOfMonthForGraph(days:Int): ArrayList<DateStartEndForGraph> {

        return getCalculatedMonthForGraph("","yyyy-MM-dd", days)


    }

    fun getCalculatedDate(date: String, dateFormat: String, days: Int): ArrayList<DateStartEnd> {
        var mDays=ArrayList<DateStartEnd>()
        for(i in days downTo 1)
        {

        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        if (date.isNotEmpty()) {
            cal.time = s.parse(date)
        }
        cal.add(Calendar.DAY_OF_YEAR, -i)

            val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            var date=s.format(Date(cal.timeInMillis))
            var mStartDate=date+" 00:00:01"
            var mEndDate=date+" 23:59:59"
            var mStartTimeInMili=sdf.parse(mStartDate)
            var mEndTimeInMili=sdf.parse(mEndDate)
            mDays.add(
                DateStartEnd(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time
                )
            )

        }
        return mDays
    }



    fun getCalculatedDateForGraph(date: String, dateFormat: String, days: Int): ArrayList<DateStartEndForGraph> {
        var mDays=ArrayList<DateStartEndForGraph>()
        for(i in days-1 downTo 1)
        {

            val cal = Calendar.getInstance()
            val s = SimpleDateFormat(dateFormat)
            if (date.isNotEmpty()) {
                cal.time = s.parse(date)
            }
            cal.add(Calendar.DAY_OF_YEAR, -i)

            val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            var date=s.format(Date(cal.timeInMillis))
            var mStartDate=date+" 00:00:01"
            var mEndDate=date+" 23:59:59"
            var mStartTimeInMili=sdf.parse(mStartDate)
            var mEndTimeInMili=sdf.parse(mEndDate)

            val wekday: Int = cal.get(Calendar.DAY_OF_WEEK)
            val dfsWekdy = DateFormatSymbols()

            mDays.add(
                DateStartEndForGraph(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time,
                    dfsWekdy.getWeekdays()[wekday].substring(0,1)
                )
            )

        }


        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        if (date.isNotEmpty()) {
            cal.time = s.parse(date)
        }


        val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        var date=s.format(Date(cal.timeInMillis))
        var mStartDate=date+" 00:00:01"
        var mEndDate=date+" 23:59:59"
        var mStartTimeInMili=sdf.parse(mStartDate)
        var mEndTimeInMili=sdf.parse(mEndDate)

        val wekday: Int = cal.get(Calendar.DAY_OF_WEEK)
        val dfsWekdy = DateFormatSymbols()


        mDays.add(
            DateStartEndForGraph(
                mStartDate,
                mEndDate,
                mStartTimeInMili.time,
                mEndTimeInMili.time,
                dfsWekdy.getWeekdays()[wekday].substring(0,1)
            )
        )


        return mDays
    }

    fun getCalculatedMonthForGraph(date: String, dateFormat: String, days: Int): ArrayList<DateStartEndForGraph> {
        var mDays=ArrayList<DateStartEndForGraph>()
        for(i in days-1 downTo 1)
        {

            val cal = Calendar.getInstance()
            val s = SimpleDateFormat(dateFormat)
            if (date.isNotEmpty()) {
                cal.time = s.parse(date)
            }
            cal.add(Calendar.MONTH, -i)

            val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


            val lastDate: Int = cal.getActualMaximum(Calendar.DATE)

            val firstDate: Int = cal.getActualMinimum(Calendar.DATE)

            cal.set(Calendar.DATE, firstDate)
            var calendarFirstDate=s.format(Date(cal.timeInMillis));
            cal.set(Calendar.DATE, lastDate)
            var calendarlastDate=s.format(Date(cal.timeInMillis));

            var date=s.format(Date(cal.timeInMillis))
            var mStartDate=calendarFirstDate+" 00:00:01"
            var mEndDate=calendarlastDate+" 23:59:59"
            var mStartTimeInMili=sdf.parse(mStartDate)
            var mEndTimeInMili=sdf.parse(mEndDate)

            val wekday: Int = cal.get(Calendar.DAY_OF_WEEK)
            val dfsWekdy = DateFormatSymbols()

            mDays.add(
                DateStartEndForGraph(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time,
                    dfsWekdy.getWeekdays()[wekday].substring(0,1)
                )
            )

        }


        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        if (date.isNotEmpty()) {
            cal.time = s.parse(date)
        }


        val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


        val lastDate: Int = cal.getActualMaximum(Calendar.DATE)

        val firstDate: Int = cal.getActualMinimum(Calendar.DATE)

        cal.set(Calendar.DATE, firstDate)
        var calendarFirstDate=s.format(Date(cal.timeInMillis));
        cal.set(Calendar.DATE, lastDate)
        var calendarlastDate=s.format(Date(cal.timeInMillis));

        var date=s.format(Date(cal.timeInMillis))
        var mStartDate=calendarFirstDate+" 00:00:01"
        var mEndDate=calendarlastDate+" 23:59:59"
        var mStartTimeInMili=sdf.parse(mStartDate)
        var mEndTimeInMili=sdf.parse(mEndDate)

        val wekday: Int = cal.get(Calendar.DAY_OF_WEEK)
        val dfsWekdy = DateFormatSymbols()

        mDays.add(
            DateStartEndForGraph(
                mStartDate,
                mEndDate,
                mStartTimeInMili.time,
                mEndTimeInMili.time,
                dfsWekdy.getWeekdays()[wekday].substring(0,1)
            )
        )


        return mDays
    }

    class DateStartEnd(var mStartDate:String,var mEndDate:String,var mStartTimeInMili:Long,var mEndTimeInMili:Long){

    }

    class DateStartEndForGraph(var mStartDate:String,var mEndDate:String,var mStartTimeInMili:Long,var mEndTimeInMili:Long,var wekday:String){

    }

    class MonthStartEndForGraph(var mStartDate:String,var mEndDate:String,var mStartTimeInMili:Long,var mEndTimeInMili:Long,var wekday:String){

    }
}