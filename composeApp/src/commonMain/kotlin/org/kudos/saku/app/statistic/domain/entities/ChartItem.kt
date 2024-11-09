package org.kudos.saku.app.statistic.domain.entities

import androidx.compose.ui.graphics.Color


data class ChartItem<T>(val data: T, val isShowed: Boolean, val chartColor: Color)
