package org.kudos.saku.app.presentation.widgets.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import kotlinx.datetime.LocalDate

@Composable
fun DatePicker(
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDateWheelPicker by remember { mutableStateOf(false) }
    WheelDatePicker(modifier) { snappedDate -> onDateSelected(snappedDate) }
}
