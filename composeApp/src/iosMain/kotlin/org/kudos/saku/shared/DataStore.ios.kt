package org.kudos.saku.shared

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class DataStorePlatform {
    actual fun createPlatformDataStore(): DataStore<Preferences> = createDataStore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
    }
}