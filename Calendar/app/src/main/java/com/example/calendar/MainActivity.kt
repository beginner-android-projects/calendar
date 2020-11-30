package com.example.calendar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.TextView
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

        picker(binding, this@MainActivity)
    }

    private fun picker(binding : ActivityMainBinding, context : Context) {
        binding.tvMonth.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
            val layout = LayoutInflater.from(context).inflate(R.layout.layout_picker, null)

            val yearPicker = layout.findViewById<NumberPicker>(R.id.year_picker)
            yearPicker.minValue=2000
            yearPicker.maxValue=2100
            yearPicker.value=2020
            yearPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            yearPicker.wrapSelectorWheel = false

            val monthPicker = layout.findViewById<NumberPicker>(R.id.month_picker)
            monthPicker.minValue=1
            monthPicker.maxValue=12
            monthPicker.value=11
            monthPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            monthPicker.wrapSelectorWheel = false

            builder.setView(layout)

            val dialog = builder.create()

            layout.findViewById<TextView>(R.id.tv_picker_cancel).setOnClickListener {
                dialog.dismiss()
            }

            layout.findViewById<TextView>(R.id.tv_picker_confirm).setOnClickListener {
                calendarViewModel.picker(yearPicker.value, monthPicker.value)
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
    }
}