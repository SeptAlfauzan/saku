package org.kudos.saku.app.domain.entities

data class MonthlyCashFlow(
    val month: String,
    val cashIn: Long,
    val cashOut: Long,
    val difference: Long
)
