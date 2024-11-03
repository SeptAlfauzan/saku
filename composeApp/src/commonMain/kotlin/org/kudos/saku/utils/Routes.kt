package org.kudos.saku.utils

sealed class Routes(val route: String){
    object Home : Routes(route = "home")
    object Statistic : Routes(route = "statistic"){}
}