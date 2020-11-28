package com.example.calendar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val calendarViewModel : CalendarViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.calendarViewModel = calendarViewModel
        binding.calendarAdapter = CalendarAdapter()
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        calendarViewModel.textMonth.observe(this, Observer { month ->
            month?.let{calendarViewModel.getDayList()}
        })

        calendarViewModel.textYear.observe(this, Observer { year ->
            year?.let{calendarViewModel.getDayList()}
        })
    }
}