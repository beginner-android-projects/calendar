package com.example.calendar

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView

object CalendarPicker {

    private lateinit var layout : View
    private lateinit var builder : AlertDialog.Builder
    private lateinit var dialog : AlertDialog

    private val color = ColorDrawable(Color.TRANSPARENT)
    private val inset = InsetDrawable(color, 120)

    fun setLayout(context : Context) {
        builder = AlertDialog.Builder(context)
        layout = View.inflate(context, R.layout.layout_picker, null)

        builder.setView(layout)

        dialog = builder.create()
    }

    fun setPicker(calendarViewModel : CalendarViewModel) {
        val yearPicker = layout.findViewById<NumberPicker>(R.id.year_picker)
        yearPicker.minValue = calendarViewModel.textYear.value!! - 20
        yearPicker.maxValue = calendarViewModel.textYear.value!! + 20
        yearPicker.value = calendarViewModel.textYear.value!!
        yearPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        yearPicker.wrapSelectorWheel = false

        val monthPicker = layout.findViewById<NumberPicker>(R.id.month_picker)
        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = calendarViewModel.textMonth.value!!
        monthPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        monthPicker.wrapSelectorWheel = false

        layout.findViewById<TextView>(R.id.tv_picker_cancel).setOnClickListener {
            dialog.dismiss()
        }

        layout.findViewById<TextView>(R.id.tv_picker_confirm).setOnClickListener {
            calendarViewModel.picker(yearPicker.value, monthPicker.value)
            dialog.dismiss()
        }
    }

    fun show() {
        dialog.window?.setBackgroundDrawable(inset)
        dialog.show()
    }
}