package com.example.calendar

import java.util.*

object CalendarDataCheck {
    private val calendar : Calendar = Calendar.getInstance()
    private val nowDay = calendar.get(Calendar.DATE)
    private val nowMonth = calendar.get(Calendar.MONTH)+1
    private val nowYear = calendar.get(Calendar.YEAR)

    fun setNowMonth(year : Int, month : Int) {
        calendar.set(year, month-1, 1)
    }

    fun getFirstDay() = calendar.get(Calendar.DAY_OF_WEEK) - 1

    fun getLastDay() = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    fun calendarTodayCheck(year : Int, month : Int, day : Int)
        = (year == nowYear && month == nowMonth && day == nowDay)

    fun calendarPreviousIndexCheck(year : Int, month : Int, index : Int) : Int {
        when (month) {
            1 -> calendar.set(year, 12, 1)
            else -> calendar.set(year, month - 2, 1)
        }
        return  calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - index + 1
    }
}