package org.kudos.saku.utils

// iosMain/kotlin/com/example/date/DateFormatter.kt
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDate
import platform.Foundation.NSISO8601DateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.dateWithTimeIntervalSince1970

actual class DateFormatter {
    actual fun formatYearMonth(yearMonth: String): String {
        val dateFormatter = NSDateFormatter().apply {
            dateFormat = "yyyy-MM"
            locale = NSLocale.localeWithLocaleIdentifier("en_US_POSIX")
        }

        val outputFormatter = NSDateFormatter().apply {
            dateFormat = "MMMM yyyy"
            locale = NSLocale.localeWithLocaleIdentifier("en_US")
        }

        val date = dateFormatter.dateFromString(yearMonth)
        return date?.let { outputFormatter.stringFromDate(it) } ?: yearMonth
    }
}
