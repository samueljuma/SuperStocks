import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import screens.StockDetailsScreen
import viewmodel.StocksViewModel

@Composable
fun AppNavigation() {
    val viewModel: StocksViewModel =
        getViewModel(Unit, viewModelFactory { StocksViewModel() })
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
            viewModel.stockUiState.value.selectedCompany?.let {
                StockDetailsScreen(it)
            }
//
        }
    }

}