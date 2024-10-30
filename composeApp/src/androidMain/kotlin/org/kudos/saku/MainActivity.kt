package org.kudos.saku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import org.kudos.saku.app.data.repositories.CashFlowRepositoryImpl
import org.kudos.saku.app.data.source.local.room.getDao
import org.kudos.saku.app.domain.repositories.CashFlowRepository
import org.kudos.saku.app.presentation.viewmodels.CashFlowViewModel
import org.kudos.saku.app.presentation.views.home.Home
import org.kudos.saku.utils.UIState
import org.kudos.saku.utils.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        actionBar?.hide()
        Napier.base(DebugAntilog())
        val dao = getDao(applicationContext).cashFlowDao()
        val cashFlowRepository: CashFlowRepository = CashFlowRepositoryImpl(dao)
        setContent {
            val viewModel: CashFlowViewModel by viewModels {
                ViewModelFactory<CashFlowRepository>(
                    cashFlowRepository
                )
            }
            App(cashFlowViewModel = viewModel)
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun HomePreview() {
//    Home(
//        {},
//        MutableStateFlow(UIState.Loading),
//    )
}