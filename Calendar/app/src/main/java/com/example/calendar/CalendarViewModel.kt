package com.example.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CalendarViewModel : ViewModel() {
    var calendar : Calendar = Calendar.getInstance()

    private val _textMonth = MutableLiveData<Int>(calendar.get(Calendar.MONTH)+1)
    val textMonth : LiveData<Int>
        get() = _textMonth

    private val _textYear = MutableLiveData<Int>(calendar.get(Calendar.YEAR))
    val textYear : LiveData<Int>
        get() = _textYear

    fun prevMonth() {
        if(_textMonth.value==1){
            _textMonth.value = 12
            _textYear.value = _textYear.value!! - 1
        } else
            _textMonth.value = _textMonth.value!! - 1
    }

    fun nextMonth() {
        if(_textMonth.value==12){
            _textMonth.value = 1
            _textYear.value = _textYear.value!! + 1
        } else
            _textMonth.value = _textMonth.value!! + 1
    }
}