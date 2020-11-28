package com.example.calendar

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object CalendarBinding {
    @BindingAdapter("nowYear", "nowMonth")
    @JvmStatic
    fun nowYearMonth(textView : TextView, year : Int, month : Int) {
        textView.text = String.format(textView.context.resources.getString(R.string.yearMonth), year, month)
    }

    @BindingAdapter("setAdapter")
    @JvmStatic
    fun setAdapter(recyclerView : RecyclerView, adapter : CalendarAdapter) {
        recyclerView.adapter = adapter
    }

    @BindingAdapter("setDays")
    @JvmStatic
    fun setDays(recyclerView : RecyclerView, list : List<CalendarData>?) {
         if (recyclerView.adapter != null) with(recyclerView.adapter as CalendarAdapter) { list?.let{ setDays(it) }}
    }

    @BindingAdapter("setDayText")
    @JvmStatic
    fun setDayText(textView : TextView, day : Int) {
        textView.text = day.toString()
    }

    @BindingAdapter("isNowMonth")
    @JvmStatic
    fun isNowMonth(textView : TextView, isNowMonth : Boolean) {
        when {
            !isNowMonth -> textView.alpha = 0.3f
            else -> textView.alpha = 1f
        }
    }

    @BindingAdapter("dayTextColor")
    @JvmStatic
    fun dayTextColor(textView : TextView, date : Int) {
        when {
            date%7==0 -> textView.setTextColor(Color.RED)
            date%7==6 -> textView.setTextColor(Color.BLUE)
            else -> textView.setTextColor(Color.BLACK)
        }
    }

    @BindingAdapter("isToday")
    @JvmStatic
    fun isToday(textView : TextView, isToday : Boolean) {
        if(isToday) {
            textView.setBackgroundResource(R.drawable.background_today)
            textView.setTextColor(Color.WHITE)
        }
    }

    @BindingAdapter("setCalendarViewModel", "setCalendar")
    @JvmStatic
    fun setCalendar(recyclerView : RecyclerView, calendarViewModel : CalendarViewModel, setCalendar : Boolean) {
        recyclerView.let { if(setCalendar) { calendarViewModel.getDayList() } }
    }
}