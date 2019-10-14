package com.example.legomvvm.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter fun calendatToDatestamp(calendar: Calendar) : Long = calendar.timeInMillis

    @TypeConverter fun datestampsToCalendar(value: Long) : Calendar = Calendar.getInstance().apply { timeInMillis = value }
}