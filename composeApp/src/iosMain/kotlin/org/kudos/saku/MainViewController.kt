package org.kudos.saku

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.kudos.saku.app.data.source.local.room.getDao

fun MainViewController() = ComposeUIViewController {
    Napier.base(DebugAntilog())
    val dao = remember {
        getDao().cashFlowDao()
    }
    App(dao)
}