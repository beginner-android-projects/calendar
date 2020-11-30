package com.example.calendar

import androidx.recyclerview.widget.DiffUtil

class CalendarDiffUtil(private val oldList : List<CalendarData>, private val newList : List<CalendarData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].day == newList[newItemPosition].day
}