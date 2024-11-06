package org.kudos.saku.app.presentation.widgets.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.darkokoa.datetimewheelpicker.WheelDatePicker
import kotlinx.datetime.LocalDate

@Composable
fun DatePicker(
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    WheelDatePicker(modifier) { snappedDate -> onDateSelected(snappedDate) }
}
