package com.sivan.pokebolt.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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


fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, 80, 80)
        val bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}