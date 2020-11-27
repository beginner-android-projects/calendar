package com.example.calendar

data class CalendarData (
    val year : Int,
    val month : Int,
    val day : Int,
    val date : Int,
    val isToday : Boolean,
    val isNowMonth : Boolean
)