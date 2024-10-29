package org.kudos.saku.app.data.source.local.room

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getDao(context: Context): Database {
    val dbFile = context.getDatabasePath("saku.db")
    return Room.databaseBuilder<Database>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}