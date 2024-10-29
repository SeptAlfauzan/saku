package org.kudos.saku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.kudos.saku.app.data.source.local.room.getDao
import org.kudos.saku.app.presentation.views.home.Home

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        actionBar?.hide()

        val dao = getDao(applicationContext).cashFlowDao()
        setContent {
            App(dao)
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun HomePreview() {
    Home(listOf())
}