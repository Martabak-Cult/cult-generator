package martabak.cult.generator.ui.theme

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PlatformTheme(content: @Composable () -> Unit) = DesktopMaterialTheme(content = content)

object AppTheme {
    val colors: Colors = Colors()

    class Colors(
        val backgroundDark: Color = Color.Black,
        val backgroundMedium: Color = Color.Black,
        val backgroundLight: Color = Color(0xFF4E5254),

        val material: androidx.compose.material.Colors = darkColors(
            background = backgroundDark,
            surface = backgroundMedium,
            primary = Color.White
        ),
    )
}