package com.example.calendar

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
}