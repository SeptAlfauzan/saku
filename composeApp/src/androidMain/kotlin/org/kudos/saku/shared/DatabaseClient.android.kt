package org.kudos.saku.shared

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.kudos.saku.app.data.source.local.room.Database

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseClient(private val context: Context){
    actual fun getDatabaseInstance(): Database {
        val dbFile = context.getDatabasePath("saku.db")
        return Room.databaseBuilder<Database>(
            context = context.applicationContext,
            name = dbFile.absolutePath
        )
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}