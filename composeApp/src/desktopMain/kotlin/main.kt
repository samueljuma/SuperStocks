import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.samueljuma.superstocks.appModule
import moe.tlaster.precompose.ProvidePreComposeLocals
import org.koin.core.context.startKoin

fun main() = application {
    startKoin{
        modules(appModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "SuperStocks",
    ) {
        // Provide the PreComposeLocals
        //This sets up the required
        // environment for PreCompose to function
        // correctly in your desktop application.
        ProvidePreComposeLocals {
            App()
        }

    }
}