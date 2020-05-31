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

    public fun ListOfWeekForGraph(weeks:Int):ArrayList<DateStartEnd> {

        return getWeeWeekData("","yyyy-MM-dd", weeks)


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

    fun getWeeWeekData(date: String, dateFormat: String, weeks: Int):ArrayList<DateStartEnd> {

        var mWeek=ArrayList<DateStartEnd>()

        val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val s = SimpleDateFormat(dateFormat)
        var c = Calendar.getInstance()
        //var date = Date()


        val calendarCurrentStart = Calendar.getInstance()
        while (calendarCurrentStart[Calendar.DAY_OF_WEEK] !== Calendar.SUNDAY) {
            calendarCurrentStart.add(Calendar.DATE, -1)
        }
        val start = calendarCurrentStart.time
        var startDate=s.format(Date(calendarCurrentStart.timeInMillis))

        var currentStartDate=calendarCurrentStart.time
        calendarCurrentStart.add(Calendar.DATE, 6)

        val end = calendarCurrentStart.time
        var endDate=s.format(Date(calendarCurrentStart.timeInMillis))




        var mStartDate=startDate+" 00:00:01"
        var mEndDate=endDate+" 23:59:59"
        var mStartTimeInMili=sdf.parse(mStartDate)
        var mEndTimeInMili=sdf.parse(mEndDate)

        mWeek.add(
            DateStartEnd(
                mStartDate,
                mEndDate,
                mStartTimeInMili.time,
                mEndTimeInMili.time
            )
        )


        for (i in weeks-1 downTo 1) {
            // c.time = date
            val i = c[Calendar.DAY_OF_WEEK] - c.firstDayOfWeek
            c.add(Calendar.DATE, -i - 7)
            val start = c.time
            var startDate=s.format(Date(c.timeInMillis))
            c.add(Calendar.DATE, 6)
            val end = c.time



            var endDate=s.format(Date(c.timeInMillis))
            var mStartDate=startDate+" 00:00:01"
            var mEndDate=endDate+" 23:59:59"
            var mStartTimeInMili=sdf.parse(mStartDate)
            var mEndTimeInMili=sdf.parse(mEndDate)

            mWeek.add(
                DateStartEnd(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time
                )
            )


            println("$start - $end")
            c.add(Calendar.DATE,-6)
        }

    return mWeek

    }








    class DateStartEnd(var mStartDate:String,var mEndDate:String,var mStartTimeInMili:Long,var mEndTimeInMili:Long){

    }

    class DateStartEndForGraph(var mStartDate:String,var mEndDate:String,var mStartTimeInMili:Long,var mEndTimeInMili:Long,var wekday:String){

    }

    class MonthStartEndForGraph(var mStartDate:String,var mEndDate:String,var mStartTimeInMili:Long,var mEndTimeInMili:Long,var wekday:String){

    }
}