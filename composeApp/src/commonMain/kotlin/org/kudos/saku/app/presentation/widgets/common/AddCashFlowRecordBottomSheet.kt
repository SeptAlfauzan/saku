package org.kudos.saku.app.presentation.widgets.common

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.kudos.saku.app.domain.entities.CashFlow
import org.kudos.saku.app.domain.entities.Input
import org.kudos.saku.app.presentation.widgets.calendar.DatePicker
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AddCashFlowRecordBottomSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    insertCashFlowToDB: (CashFlow, () -> Unit) -> Unit,
    isSavingCashFlow: StateFlow<Boolean>,
    onSuccess: () -> Unit,
    animationSpec: AnimationSpec<Float> = tween(300),
) {

    val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var selectedDate by remember { mutableStateOf(currentDate) }
    var descInput by remember { mutableStateOf(Input(value = "")) }
    var amountInput by remember { mutableStateOf(Input<Long>(value = 0)) }
    var isCashIn by remember { mutableStateOf(false) }

    fun resetFormValues() {
        descInput = descInput.copy(value = "")
        amountInput = amountInput.copy(value = 0)
        selectedDate = currentDate
        isCashIn = false
    }

    BottomSheet(
        showBottomSheet = showBottomSheet,
        onDismiss = onDismiss,
        animationSpec = animationSpec,
    ){
        AddCashFlowForm(
            onSelectDate = { selectedDate = it },
            onCheckCashIn = { isCashIn = it },
            isCashIn = isCashIn,
            descInput = descInput,
            amountInput = amountInput,
            isFormValid = descInput.value != "" && amountInput.value != 0L,
            onDescInputChange = {
                descInput = descInput.copy(value = it)
            },
            onAmountInputChange = {
                it.toLongOrNull()?.let { parsedValue ->
                    amountInput = amountInput.copy(value = parsedValue)
                }
            },
            onCancel = {
                resetFormValues()
                onDismiss()
            },
            onOk = {
                val item = CashFlow(
                    id = Uuid.random().toString(),
                    text = descInput.value,
                    amount = amountInput.value,
                    created = selectedDate.toString(),
                    isCashIn = isCashIn
                )
                insertCashFlowToDB(item) {
                    resetFormValues()
                    onSuccess()
                    onDismiss()
                }
            }
        )
    }
}

@Composable
private fun AddCashFlowForm(
    isCashIn: Boolean,
    onCheckCashIn: (Boolean) -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    descInput: Input<String>,
    onDescInputChange: (String) -> Unit,
    amountInput: Input<Long>,
    onAmountInputChange: (String) -> Unit,
    isFormValid: Boolean,
    onCancel: () -> Unit,
    onOk: () -> Unit,
) {

    val gray = Color(0xFFA6A6A6)
    val textGray = TextStyle(color = gray)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Text("Select date", style = textGray)
            DatePicker(
                onDateSelected = onSelectDate,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Is cash in?", style = textGray)
            Switch(
                checked = isCashIn,
                onCheckedChange = onCheckCashIn,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFFBFFF67)
                )
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Description", style = textGray)
            StyledTextField(
                value = descInput.value,
                onValueChange = onDescInputChange,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Amount", style = textGray)
            StyledTextField(
                value = amountInput.value.toString(),
                onValueChange = onAmountInputChange,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.align(Alignment.End)
        ) {
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF29515B),
                    contentColor = Color.White
                )
            ) {
                Text("cancel")
            }
            Button(
                enabled = isFormValid,
                shape = RoundedCornerShape(8.dp),
                onClick = onOk,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFBFFF67)
                )
            ) {
                Text("save")
            }
        }
    }
}