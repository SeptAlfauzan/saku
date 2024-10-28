package org.kudos.saku.app.domain.entities

data class CashFlow(
    val id: String,
    val text: String,
    val isCashIn: Boolean,
    val amount: Long
)