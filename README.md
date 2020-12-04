

# Calendar



<div style="text-align : center">
    <img src="https://user-images.githubusercontent.com/63637706/101167555-664bfd00-367d-11eb-9181-d108b8e72dee.gif" width="350" height="600">
</div>





## 1. Dependencies

<img src="https://user-images.githubusercontent.com/63637706/101167930-0013aa00-367e-11eb-9e09-4f3882f284cf.PNG">



## 2. Style

<img src="https://user-images.githubusercontent.com/63637706/101168072-410bbe80-367e-11eb-9251-f04e296f69d7.PNG"><img src="https://user-images.githubusercontent.com/63637706/101170142-57ffe000-3681-11eb-80a5-fcd844f4244c.PNG">  



## 3. CalendarData

```kotlin
data class CalendarData (
    val year : Int,
    val month : Int,
    val day : Int,
    val date : Int,
    val isToday : Boolean,
    val isNowMonth : Boolean
)
```



## 4. CalendarBinding

```kotlin
object CalendarBinding {
    @BindingAdapter("nowYear", "nowMonth")
    @JvmStatic
    fun nowYearMonth(textView : TextView, year : Int, month : Int) {
        textView.text = String.format
        (textView.context.resources.getString(R.string.yearMonth), year, month)
    }

    @BindingAdapter("setDays")
    @JvmStatic
    fun setDays(recyclerView : RecyclerView, list : List<CalendarData>?) {
         if (recyclerView.adapter != null) 
        with(recyclerView.adapter as CalendarAdapter) { 
            list?.let{ setDays(it) }}
    }

    @BindingAdapter("setDayText")
    @JvmStatic
    fun setDayText(textView : TextView, day : Int) {
        textView.text = day.toString()
    }

    @BindingAdapter("isNowMonth")
    @JvmStatic
    fun isNowMonth(textView : TextView, isNowMonth : Boolean) {
        when {
            !isNowMonth -> textView.alpha = 0.3f
            else -> textView.alpha = 1f
        }
    }

    @BindingAdapter("dayTextColor")
    @JvmStatic
    fun dayTextColor(textView : TextView, date : Int) {
        when {
            date%7==0 -> textView.setTextColor(Color.RED)
            date%7==6 -> textView.setTextColor(Color.BLUE)
            else -> textView.setTextColor(Color.BLACK)
        }
    }

    @BindingAdapter("isToday")
    @JvmStatssic
    fun isToday(textView : TextView, isToday : Boolean) {
        if(isToday) {
            textView.setBackgroundResource(R.drawable.background_today)
            textView.setTextColor(Color.WHITE)
        } else textView.setBackgroundResource(0)
    }

    @BindingAdapter("setCalendarViewModel", "setCalendar")
    @JvmStatic
    fun setCalendar(recyclerView : RecyclerView, 
              calendarViewModel : CalendarViewModel, setCalendar : Boolean) 
    {
        recyclerView.let { if(setCalendar) { calendarViewModel.getDayList() } }
    }
}
```



## 5. CalendarDataCheck

```kotlin
object CalendarDataCheck {
    private val calendar : Calendar = Calendar.getInstance()
    private val nowDay = calendar.get(Calendar.DATE)
    private val nowMonth = calendar.get(Calendar.MONTH)+1
    private val nowYear = calendar.get(Calendar.YEAR)

    fun setNowMonth(year : Int, month : Int) {
        calendar.set(year, month-1, 1)
    }

    fun getFirstDay() = calendar.get(Calendar.DAY_OF_WEEK) - 1

    fun getLastDay() = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    fun calendarTodayCheck(year : Int, month : Int, day : Int)
        = (year == nowYear && month == nowMonth && day == nowDay)

    fun calendarPreviousIndexCheck(year : Int, month : Int, index : Int) : Int {
        when (month) {
            1 -> calendar.set(year, 12, 1)
            else -> calendar.set(year, month - 2, 1)
        }
        return  calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - index + 1
    }
}
```



## 6. CalendarPicker

```kotlin
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
        yearPicker.descendantFocusability = 
            NumberPicker.FOCUS_BLOCK_DESCENDANTS
        yearPicker.wrapSelectorWheel = false

        val monthPicker = layout.findViewById<NumberPicker>(R.id.month_picker)
        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = calendarViewModel.textMonth.value!!
        monthPicker.descendantFocusability = 
           NumberPicker.FOCUS_BLOCK_DESCENDANTS
        monthPicker.wrapSelectorWheel = false

        layout.findViewById<TextView>(R.id.tv_picker_cancel).setOnClickListener
        {
            dialog.dismiss()
        }

        layout.findViewById<TextView>(R.id.tv_picker_confirm).setOnClickListener
        {
            calendarViewModel.picker(yearPicker.value, monthPicker.value)
            dialog.dismiss()
        }
    }

    fun show() {
        dialog.window?.setBackgroundDrawable(inset)
        dialog.show()
    }
}
```



## 7. CalendarDiffUtil

```kotlin
class CalendarDiffUtil(private val oldList : List<CalendarData>, private val newList : List<CalendarData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) 
    = oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)
    = oldList[oldItemPosition].day == newList[newItemPosition].day
}
```



## 8. getDayList() in CalendarViewModel

```kotlin
fun getDayList() {
        //init
        CalendarDataCheck.setNowMonth(textYear.value!!, textMonth.value!!)

        //month
        val firstIndex = CalendarDataCheck.getFirstDay()
        val lastIndex  = CalendarDataCheck.getLastDay()

        //previous_month
        var prevEmptyIndex =
    		CalendarDataCheck.calendarPreviousIndexCheck
    		(textYear.value!!, textMonth.value!!, firstIndex)

        //next_month
        var lastEmpty = firstIndex + lastIndex
        var lastEmptyIndex = 1

        val tempList = mutableListOf<CalendarData>()
        tempList.apply {
            //previous_month
            var date = 0
            for (i in 0 until firstIndex) {
                if (textMonth.value == 1) {
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = 12,
                            day = prevEmptyIndex,
                            date = date,
                            isToday = false,
                            isNowMonth = false
                        )
                    )
                } else {
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = textMonth.value!! - 1,
                            day = prevEmptyIndex,
                            date = date,
                            isToday = false,
                            isNowMonth = false
                        )
                    )
                }
                date += 1
                prevEmptyIndex += 1
            }
            //month
            for (i in 1..lastIndex) {
                if (CalendarDataCheck.calendarTodayCheck
                    (textYear.value!!, textMonth.value!!, i))
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = textMonth.value!!,
                            day = i,
                            date = date,
                            isToday = true,
                            isNowMonth = true
                        )
                    )
                else
                    add(
                        CalendarData(
                            year = textYear.value!!,
                            month = textMonth.value!!,
                            day = i,
                            date = date,
                            isToday = false,
                            isNowMonth = true
                        )
                    )
                date += 1
            }
            //next_month
            while (true) {
                if (lastEmpty % 7 != 0) {
                    if (textMonth.value!! == 12) {
                        add(
                            CalendarData(
                                year = textYear.value!! + 1,
                                month = 1,
                                day = lastEmptyIndex,
                                date = date,
                                isToday = false,
                                isNowMonth = false
                            )
                        )
                    } else {
                        add(
                            CalendarData(
                                year = textYear.value!!,
                                month = textMonth.value!! + 1,
                                day = lastEmptyIndex,
                                date = date,
                                isToday = false,
                                isNowMonth = false
                            )
                        )
                    }
                    lastEmptyIndex += 1
                    lastEmpty += 1
                    date += 1
                } else break
            }
            _calendarData.value = tempList
        }
    }
```