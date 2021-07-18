package com.sivan.pokebolt.util

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateTimeConverter {

    @TypeConverter
    fun fromLocalDateToString(date : LocalDate) : String {
        return date.toString()
    }

    @TypeConverter
    fun fromStringToLocalDate(dateString : String) : LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun fromLocalDateTimeToString(date : LocalDateTime) : String {
        return date.toString()
    }

    @TypeConverter
    fun fromStringToLocalDateTime(dateString : String) : LocalDateTime {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.systemDefault()))
    }


    @TypeConverter
    fun fromZonedDateTimeToString(value : ZonedDateTime) : String {
        return value.toString()
    }

    @TypeConverter
    fun fromStringToZonedDateTime(value : String) : ZonedDateTime {
        return ZonedDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of("Z")))
    }
}