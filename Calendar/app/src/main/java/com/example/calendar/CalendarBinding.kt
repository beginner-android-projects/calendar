package com.example.calendar

import android.widget.TextView
import androidx.databinding.BindingAdapter

object CalendarBinding {
    @BindingAdapter("nowYear", "nowMonth")
    @JvmStatic
    fun nowYearMonth(textView : TextView, year : Int, month : Int) {
        textView.text = "$year / $month"
    }
}