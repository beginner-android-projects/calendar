package com.example.calendar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.calendar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val calendarViewModel : CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.calendarViewModel = calendarViewModel
        binding.calendarAdapter = CalendarAdapter()
        binding.lifecycleOwner = this

        binding.btnSelectMonth.setOnClickListener {
            CalendarPicker.apply{
                setLayout(this@MainActivity)
                setPicker(calendarViewModel)
                show()
            }
        }
    }
}