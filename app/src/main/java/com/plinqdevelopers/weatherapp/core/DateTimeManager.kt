package com.plinqdevelopers.weatherapp.core

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private val localDateTime = LocalDateTime.now()

fun getCurrentFormattedTime(): String {
    return "${localDateTime.toLocalTime().hour}:${localDateTime.toLocalTime().minute} ${localDateTime.toLocalTime().format(
        DateTimeFormatter.ofPattern("a"),
    )}"
}

fun getCurrentFormattedDate(): String {
    val dayOfWeek = localDateTime.dayOfWeek.toString().lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    val dayOfMonth = localDateTime.dayOfMonth
    val month = localDateTime.month.toString().lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val year = localDateTime.year
    return "$dayOfWeek, $dayOfMonth $month $year"
}
