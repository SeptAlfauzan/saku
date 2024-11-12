package org.kudos.saku.shared

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class DataStorePlatform(private val context: Context) {
    actual fun createPlatformDataStore(): DataStore<Preferences> = createDataStore {
        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
    }
}