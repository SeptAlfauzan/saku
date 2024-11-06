package org.kudos.saku.shared

import androidx.room.InvalidationTracker
import androidx.room.Room
import org.kudos.saku.app.data.source.local.room.Database
import org.kudos.saku.app.data.source.local.room.dao.CashFlowDao

actual class DatabaseClient{
    actual fun getDatabaseInstance(): Database {
        val dbFile = NSHomeDirectory() + "/saku.db"
        return Room.databaseBuilder<Database>(
            name = dbFile,
            factory = { Database::class.instantiateImpl() }
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}