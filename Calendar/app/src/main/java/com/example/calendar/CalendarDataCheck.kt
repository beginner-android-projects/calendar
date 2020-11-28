package com.example.calendar

import java.util.*

object CalendarDataCheck {
    var calendar : Calendar = Calendar.getInstance()
    private var nowDay = calendar.get(Calendar.DATE)
    private var nowMonth = calendar.get(Calendar.MONTH)+1
    private var nowYear = calendar.get(Calendar.YEAR)
    private var endDay = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    fun getFirstDay(year : Int, month : Int) : Int{
        val preYear = year-1
        var daySum = preYear*365 + preYear/4 - preYear/100 + preYear/400

        if (month>=3 && (year%4==0 && year%100!=0 || year%400 ==0)) daySum += 1

        val preMonth = month - 1

        for (i in 0 until preMonth) daySum += endDay[i]

        daySum += 1

        return daySum % 7
    }

    fun getLastDay(year : Int, month : Int) : Int {
        calendar.set(year, month-1,1)
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun calendarTodayCheck(i : Int, year : Int, month : Int) : Boolean { //today
        return (i == nowDay && year == nowYear && month == nowMonth)
    }

    fun calendarPreviousIndexCheck(month : Int, index : Int) : Int { //prev_month start day
        return when (month) {
            1 -> endDay[11] - index + 1
            else -> endDay[month-2] - index + 1
        }
    }
}