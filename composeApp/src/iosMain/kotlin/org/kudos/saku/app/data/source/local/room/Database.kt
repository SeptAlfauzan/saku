package org.kudos.saku.app.data.source.local.room

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getDao(): Database {
    val dbFile = NSHomeDirectory() + "/saku.db"
    return Room.databaseBuilder<Database>(
        name = dbFile,
        factory = { Database::class.instantiateImpl() }
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}