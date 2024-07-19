import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import moe.tlaster.precompose.ProvidePreComposeLocals

fun main() = application {
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