package com.sivan.pokebolt.util

import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun Any?.toLocalTime12Hr(): String {
    //val dataTime = "2021-01-19T13:27:41.747Z"

    if (this == null) return "null"

    val zoneUTC: ZoneId = ZoneId.of("Z") // Z as UTC
    val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME.withZone(zoneUTC)

    var zonedDateTime: ZonedDateTime = ZonedDateTime.parse(this.toString(), dateTimeFormatter)
    zonedDateTime = zonedDateTime.toOffsetDateTime().atZoneSameInstant(ZoneId.systemDefault())


    val result24Hr = LocalTime.of(zonedDateTime.hour, zonedDateTime.minute)
    val result12Hr =
        LocalTime.parse(result24Hr.toString(), DateTimeFormatter.ofPattern("HH:mm")).format(
            DateTimeFormatter.ofPattern("hh:mm a")
        )
    return result12Hr
}

fun Any?.toDate(): String {
    //val dataTime = "2021-01-19T13:27:41.747Z"

    if (this == null) return "null"

    val zoneUTC: ZoneId = ZoneId.of("Z") // Z as UTC
    val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME.withZone(zoneUTC)

    var zonedDateTime: ZonedDateTime = ZonedDateTime.parse(this.toString(), dateTimeFormatter)
    zonedDateTime = zonedDateTime.toOffsetDateTime().atZoneSameInstant(ZoneId.systemDefault())

    return "${zonedDateTime.dayOfMonth}-${zonedDateTime.monthValue}-${zonedDateTime.year}"
}