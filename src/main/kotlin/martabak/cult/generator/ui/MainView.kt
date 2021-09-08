package martabak.cult.generator.ui

import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import martabak.cult.generator.ui.theme.AppTheme
import martabak.cult.generator.ui.theme.PlatformTheme

@Composable
fun MainView() {

    DisableSelection {
        MaterialTheme(
            colors = AppTheme.colors.material
        ) {
            PlatformTheme {
                Surface {
                    GeneratorView()
                }
            }
        }
    }
}