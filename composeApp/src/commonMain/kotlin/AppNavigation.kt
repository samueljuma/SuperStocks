import androidx.compose.runtime.Composable
import data.network.KtorClient
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import repository.TickerDetailsRepository
import screens.StockDetailsScreen
import viewmodel.NewViewModel
import viewmodel.StocksViewModel

@Composable
fun AppNavigation() {
    val viewModel: NewViewModel = koinViewModel()
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        initialRoute = "home"
    ) {
        scene(
            route = "home",
            navTransition = NavTransition(),
        ) {
            StockListScreen(
                viewModel,
                onItemClick = { profile ->

//                    val encodedProfile = Json.encodeToString(profile)
                    navigator.navigate(route = "detail")
                }
            )
        }
        scene(
            route = "detail",
            navTransition = NavTransition(),
        ) {
            viewModel.selectedCompany.value?.let {
                StockDetailsScreen(
                    it,
                    onBackPressed = {
                        navigator.popBackStack()
                    }
                )
            }
        }
    }

}