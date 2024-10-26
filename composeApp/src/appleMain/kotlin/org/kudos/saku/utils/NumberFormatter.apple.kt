package org.kudos.saku.utils

actual class NumberFormatter actual constructor() {
    actual fun formatIDR(amount: Long): String {
        return try {
            val formatter = platform.Foundation.NSNumberFormatter().apply {
                numberStyle = platform.Foundation.NSNumberFormatterStyle.NSNumberFormatterCurrencyStyle
                locale = platform.Foundation.NSLocale("id_ID")
                currencySymbol = "Rp"
                maximumFractionDigits = 0
            }
            formatter.stringFromNumber(amount.toNSNumber()) ?: "Rp0"
        } catch (e: Exception) {
            "Rp0"
        }
    }
}