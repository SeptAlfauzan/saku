package org.kudos.saku

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.kudos.saku.app.data.source.local.room.getDao
import org.kudos.saku.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    Napier.base(DebugAntilog())
    App()
}