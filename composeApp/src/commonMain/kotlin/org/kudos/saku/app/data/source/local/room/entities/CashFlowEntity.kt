package org.kudos.saku.app.data.source.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity
data class CashFlowEntity @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey() val id: String = Uuid.toString(),
    val text: String,
    val isCashIn: Boolean,
    val amount: Long,
    val created: String,
)