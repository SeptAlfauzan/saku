package org.kudos.saku.utils

import com.kizitonwose.calendar.core.YearMonth


fun Long.toIdr(): String {
    val formatter = NumberFormatter()
    return formatter.formatIDR(this)
}

fun YearMonth.formatDate(): String {
    val formatter = DateFormatter()
    return formatter.formatYearMonth(this.toString())
}

fun String.advancedTitleCase(): String {
    // Define word separators
    val separators = setOf(' ', '-', '_', '.')
    return lowercase()
        .split(*separators.toCharArray())
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
}