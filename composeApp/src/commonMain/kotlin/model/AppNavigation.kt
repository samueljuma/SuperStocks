package model

import StockDetailsScreen
import StockListScreen
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun AppNavigation(){
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        initialRoute = "home"
    ){
        scene(
            route = "home",
            navTransition = NavTransition(),
        ){
            StockListScreen(
                onItemClick = {
                    navigator.navigate(route = "detail", options = null)
                }
            )
        }
        scene(
            route = "detail",
            navTransition = NavTransition(),
        ){
            StockDetailsScreen()
        }
    }
}