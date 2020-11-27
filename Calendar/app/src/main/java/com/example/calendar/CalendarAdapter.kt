package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.databinding.ItemDayBinding

class CalendarAdapter(private var list: List<CalendarData>) : RecyclerView.Adapter<CalendarAdapter.VHolder>(){

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int)
            = VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class VHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding : ItemDayBinding = DataBindingUtil.bind(itemView)!!

        fun bind(calendarData : CalendarData) {
            binding.setVariable(BR.calendarData, calendarData)
        }
    }
}