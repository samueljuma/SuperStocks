import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    PreComposeApp {
        MaterialTheme {
            AppNavigation()
        }
    }
}


