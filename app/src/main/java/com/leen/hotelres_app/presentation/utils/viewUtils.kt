package com.leen.hotelres_app.presentation.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit

object viewUtils {
    fun convertirEpochEnDateFormattee(epochMillis: Long): String{
        val systemZoneId = ZoneId.systemDefault()
        val instant = Instant.ofEpochMilli(epochMillis + 1.days.toLong(DurationUnit.MILLISECONDS))
        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.MEDIUM)
            .withLocale(Locale.getDefault())
        return instant.atZone(systemZoneId).toLocalDate().format(formatter)
    }
}