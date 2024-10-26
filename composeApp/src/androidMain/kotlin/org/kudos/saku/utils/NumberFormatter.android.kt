package org.kudos.saku.utils

actual class NumberFormatter actual constructor() {
    actual fun formatIDR(amount: Long): String {
        return try {
            val formatter = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("id", "ID"))
            formatter.format(amount)
        } catch (e: Exception) {
            "Rp0"
        }
    }
}
