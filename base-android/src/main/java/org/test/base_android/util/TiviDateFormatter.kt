package org.test.base_android.util

import android.text.format.DateUtils
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal

class TiviDateFormatter constructor(
    private val shortTimeFormatter: DateTimeFormatter,
    private val shortDateFormatter: DateTimeFormatter,
    private val mediumDateFormatter: DateTimeFormatter,
    private val mediumDateTimeFormatter: DateTimeFormatter
) {
    fun formatShortDate(temporalAmount: Temporal): String =
        shortDateFormatter.format(temporalAmount)

    fun formatMediumDate(temporalAmount: Temporal): String =
        mediumDateFormatter.format(temporalAmount)

    fun formatMediumDateTime(temporalAmount: Temporal): String =
        mediumDateTimeFormatter.format(temporalAmount)

    fun formatShortTime(localTime: LocalTime): String = shortTimeFormatter.format(localTime)

    fun formatShortRelativeTime(dateTime: OffsetDateTime): String {
        val now = OffsetDateTime.now()

        return if (dateTime.isBefore(now)) {
            if (dateTime.year == now.year || dateTime.isAfter(now.minusDays(7))) {
                // Within the past week
                DateUtils.getRelativeTimeSpanString(
                    dateTime.toInstant().toEpochMilli(),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_SHOW_DATE
                ).toString()
            } else {
                // More than 7 days ago
                formatShortDate(dateTime)
            }
        } else {
            if (dateTime.year == now.year || dateTime.isBefore(now.plusDays(14))) {
                // In the near future (next 2 weeks)
                DateUtils.getRelativeTimeSpanString(
                    dateTime.toInstant().toEpochMilli(),
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_SHOW_DATE
                ).toString()
            } else {
                // In the far future
                formatShortDate(dateTime)
            }
        }
    }
}
