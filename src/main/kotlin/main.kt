import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import martabak.cult.generator.ui.MainView


@OptIn(ExperimentalComposeUiApi::class)
fun main() = singleWindowApplication(
    title = "Cult Generator",
    state = WindowState(width = 1280.dp, height = 768.dp),
) {
    MainView()
}