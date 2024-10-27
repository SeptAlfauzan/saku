package org.kudos.saku.utils

import android.annotation.SuppressLint
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

actual class DateFormatter {
    @SuppressLint("NewApi")
    actual fun formatYearMonth(yearMonth: String): String {
        val parsed = YearMonth.parse(yearMonth)
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
        return parsed.format(formatter)
    }
}
