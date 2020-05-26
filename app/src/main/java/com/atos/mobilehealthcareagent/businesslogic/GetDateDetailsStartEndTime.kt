package com.atos.mobilehealthcareagent.businesslogic

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GetDateDetailsStartEndTime {


    public fun ListOfDays(days: Int): ArrayList<DateStartEnd> {

        return getCalculatedDate("", "yyyy-MM-dd", days)


    }


    public fun ListOfMonths(days: Int): ArrayList<DateStartEnd> {

        return getlastSevenMonthsStartEndDate("", "yyyy-MM-dd", days)


    }


    fun getCalculatedDate(date: String, dateFormat: String, days: Int): ArrayList<DateStartEnd> {
        var mDays = ArrayList<DateStartEnd>()
        for (i in days - 1 downTo 1) {

            val cal = Calendar.getInstance()
            val s = SimpleDateFormat(dateFormat)
            if (date.isNotEmpty()) {
                cal.time = s.parse(date)
            }
            cal.add(Calendar.DAY_OF_YEAR, -i)

            val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            var date = s.format(Date(cal.timeInMillis))
            var mStartDate = date + " 00:00:01"
            var mEndDate = date + " 23:59:59"
            var mStartTimeInMili = sdf.parse(mStartDate)
            var mEndTimeInMili = sdf.parse(mEndDate)

            val wekdy = SimpleDateFormat("EEE")
            val stringDate = wekdy.format(Date(cal.timeInMillis)).substring(0,1).toString()

            mDays.add(
                DateStartEnd(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time,
                    stringDate,
                    stringDate
                )
            )

        }


        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        if (date.isNotEmpty()) {
            cal.time = s.parse(date)
        }
        if (mDays.size == days - 1) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            var date = s.format(Date(cal.timeInMillis))
            var mStartDate = date + " 00:00:01"
            var mEndDate = date + " 23:59:59"
            var mStartTimeInMili = sdf.parse(mStartDate)
            var mEndTimeInMili = sdf.parse(mEndDate)
            val wekdy = SimpleDateFormat("EEE")
            val stringDate = wekdy.format(Date(cal.timeInMillis)).substring(0,1).toString()

            mDays.add(
                DateStartEnd(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time,
                    stringDate,
                    stringDate
                )
            )
        }



        return mDays
    }


    fun getlastSevenMonthsStartEndDate(
        date: String,
        dateFormat: String,
        months: Int
    ): ArrayList<DateStartEnd> {
        var mDays = ArrayList<DateStartEnd>()
        var calendar = Calendar.getInstance()
        for (i in months downTo 1) {
            val dfs = DateFormatSymbols()


            val s = SimpleDateFormat(dateFormat)


            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            // calendar = Calendar.getInstance()
            var lastDate: Int = calendar.getActualMaximum(Calendar.DATE)
            calendar.set(Calendar.DATE, lastDate)

            var date = s.format(Date(calendar.timeInMillis))

            var mEndDate = date + " 23:59:59"

            var mEndTimeInMili = sdf.parse(mEndDate)

            var firstDate: Int = calendar.getActualMinimum(Calendar.DATE)
            calendar.set(Calendar.DATE, firstDate)

            date = s.format(Date(calendar.timeInMillis))
            var mStartDate = date + " 00:00:01"

            var mStartTimeInMili = sdf.parse(mStartDate)



            calendar.set(Calendar.DATE, lastDate)
            var lastDay: Int = calendar.get(Calendar.DAY_OF_WEEK)


            System.out.println("Last Date: " + calendar.getTime())


            println("Last Day : $lastDay")


            mDays.add(
                DateStartEnd(
                    mStartDate,
                    mEndDate,
                    mStartTimeInMili.time,
                    mEndTimeInMili.time,"",""
                )
            )

            calendar.add(Calendar.MONTH, -1)


        }
        return mDays
    }

    class DateStartEnd(
        var mStartDate: String,
        var mEndDate: String,
        var mStartTimeInMili: Long,
        var mEndTimeInMili: Long,
        var mStartDaysWeekOfDay:String,
        var mEndDaysWeekOfDay:String
    ) {

    }
}