package com.sivan.pokebolt.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

fun Any?.toLocalTime12Hr(): String {

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

    if (this == null) return "null"

    val zoneUTC: ZoneId = ZoneId.of("Z") // Z as UTC
    val dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME.withZone(zoneUTC)

    var zonedDateTime: ZonedDateTime = ZonedDateTime.parse(this.toString(), dateTimeFormatter)
    zonedDateTime = zonedDateTime.toOffsetDateTime().atZoneSameInstant(ZoneId.systemDefault())

    return "${zonedDateTime.month.toString().lowercase(Locale.getDefault())
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }} ${zonedDateTime.dayOfMonth}, ${zonedDateTime.year}"
}


fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, 80, 80)
        val bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

fun getRandomLocation(): LatLng {
    val latLng = LatLng(-34.0, 151.0)
    val radius = 10000
    val x0 = latLng.latitude
    val y0 = latLng.longitude


    // Convert radius from meters to degrees
    val radiusInDegrees = (radius / 111000f).toDouble()
    val u = Random.nextDouble()
    val v = Random.nextDouble()
    val w = radiusInDegrees * Math.sqrt(u)
    val t = 2 * Math.PI * v
    val x = w * cos(t)
    val y = w * sin(t)

    // Adjust the x-coordinate for the shrinking of the east-west distances
    val new_x = x / Math.cos(y0)
    val foundLatitude = new_x + x0
    val foundLongitude = y + y0

    return LatLng(foundLatitude, foundLongitude)
}