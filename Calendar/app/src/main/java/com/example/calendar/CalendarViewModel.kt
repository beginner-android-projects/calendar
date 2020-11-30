package com.example.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CalendarViewModel : ViewModel() {
    private val calendar : Calendar = Calendar.getInstance()

    private val _textMonth = MutableLiveData<Int>(calendar.get(Calendar.MONTH)+1)
    val textMonth : LiveData<Int>
        get() = _textMonth

    private val _textYear = MutableLiveData<Int>(calendar.get(Calendar.YEAR))
    val textYear : LiveData<Int>
        get() = _textYear

    private val _calendarData= MutableLiveData<MutableList<CalendarData>>()
    val calendarData : LiveData<MutableList<CalendarData>>
        get() = _calendarData

    private val _setCalendar= MutableLiveData<Boolean>(true)
    val setCalendar : LiveData<Boolean>
        get() = _setCalendar

    fun prevMonth() {
        if(_textMonth.value==1){
            _textMonth.value = 12
            _textYear.value = _textYear.value!! - 1
        } else
            _textMonth.value = _textMonth.value!! - 1
        _setCalendar.value = true
    }

    fun nextMonth() {
        if(_textMonth.value==12){
            _textMonth.value = 1
            _textYear.value = _textYear.value!! + 1
        } else
            _textMonth.value = _textMonth.value!! + 1
        _setCalendar.value = true
    }

    fun nowMonth() {
        _textMonth.value = calendar.get(Calendar.MONTH)+1
        _textYear.value = calendar.get(Calendar.YEAR)
        _setCalendar.value = true
    }

    fun getDayList() {
        //month
        val firstIndex = CalendarDataCheck.getFirstDay(textYear.value!!, textMonth.value!!)
        val lastIndex  = CalendarDataCheck.getLastDay(textYear.value!!, textMonth.value!!)

        //previous_month
        var prevEmptyIndex = CalendarDataCheck.calendarPreviousIndexCheck(textMonth.value!!, firstIndex)

        //next_month
        var lastEmpty = firstIndex + lastIndex
        var lastEmptyIndex = 1

        val tempList = mutableListOf<CalendarData>()
        tempList.apply {
            //previous_month
            var date = 0
            for (i in 0 until firstIndex) {
                if (textMonth.value == 1) {
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = 12,
                            day = prevEmptyIndex,
                            date = date,
                            isToday = false,
                            isNowMonth = false
                        )
                    )
                } else {
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = textMonth.value!! - 1,
                            day = prevEmptyIndex,
                            date = date,
                            isToday = false,
                            isNowMonth = false
                        )
                    )
                }
                date += 1
                prevEmptyIndex += 1
            }
            //month
            for (i in 1..lastIndex) {
                if (CalendarDataCheck.calendarTodayCheck(i, textYear.value!!, textMonth.value!!))
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = textMonth.value!!,
                            day = i,
                            date = date,
                            isToday = true,
                            isNowMonth = true
                        )
                    )
                else
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = textMonth.value!!,
                            day = i,
                            date = date,
                            isToday = false,
                            isNowMonth = true
                        )
                    )
                date += 1
            }
            //next_month
            while (true) {
                if (lastEmpty % 7 != 0) {
                    if (textMonth.value!! == 12) {
                        add(
                            CalendarData(
                                year = textYear.value!! + 1,
                                month = 1,
                                day = lastEmptyIndex,
                                date = date,
                                isToday = false,
                                isNowMonth = false
                            )
                        )
                    } else {
                        add(
                            CalendarData(
                                year = textYear.value!!,
                                month = textMonth.value!! + 1,
                                day = lastEmptyIndex,
                                date = date,
                                isToday = false,
                                isNowMonth = false
                            )
                        )
                    }
                    lastEmptyIndex += 1
                    lastEmpty += 1
                    date += 1
                } else break
            }
            _calendarData.value = tempList
        }
    }
}