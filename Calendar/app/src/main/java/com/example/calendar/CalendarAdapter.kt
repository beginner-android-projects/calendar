package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.databinding.ItemDayBinding

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.VHolder>(){

    private var list = emptyList<CalendarData>()

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int)
            = VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setDays(newList : List<CalendarData>){
        val diffUtilCallBack = CalendarDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallBack)
        this.list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class VHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val binding : ItemDayBinding = DataBindingUtil.bind(itemView)!!

        fun bind(calendarData : CalendarData) {
            binding.setVariable(BR.calendarData, calendarData)
        }
    }
}