package org.kudos.saku.app.data.source.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao
import org.kudos.saku.app.data.source.local.room.entities.CashFlowEntity

@Database(entities = [CashFlowEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun cashFlowDao(): CashFlowDao
}