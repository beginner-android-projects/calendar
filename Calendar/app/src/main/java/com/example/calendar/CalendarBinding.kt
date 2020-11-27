package com.example.calendar

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object CalendarBinding {
    @BindingAdapter("nowYear", "nowMonth")
    @JvmStatic
    fun nowYearMonth(textView : TextView, year : Int, month : Int) {
        textView.text = "$year / $month"
    }

    @BindingAdapter("setAdapter")
    @JvmStatic
    fun setAdapter(recyclerView : RecyclerView, list : List<CalendarData>?) {
        list?.let{
            val adapter = CalendarAdapter(list)
            recyclerView.adapter = adapter
        }
    }

    @BindingAdapter("setDayText")
    @JvmStatic
    fun setDayText(textView : TextView, day : Int) {
        textView.text = day.toString()
    }

    @BindingAdapter("isNowMonth")
    @JvmStatic
    fun isNowMonth(textView : TextView, isNowMonth : Boolean) {
        if(!isNowMonth) textView.alpha = 0.3f
    }

    @BindingAdapter("dayTextColor")
    @JvmStatic
    fun dayTextColor(textView : TextView, date : Int) {
        if(date%7==0) textView.setTextColor(Color.RED)
        else if(date%7==6) textView.setTextColor(Color.BLUE)
    }

    @BindingAdapter("isToday")
    @JvmStatic
    fun isToday(textView : TextView, isToday : Boolean) {
        if(isToday) textView.setBackgroundResource(R.drawable.background_today)
    }
}