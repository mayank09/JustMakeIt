package me.appic.justmakeit

sealed class NavRoutes(val route: String) {
    object MainScreen : NavRoutes("home")
    object FilterListScreen : NavRoutes("filterList")
}
