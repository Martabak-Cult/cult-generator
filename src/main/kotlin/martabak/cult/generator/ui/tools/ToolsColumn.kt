package martabak.cult.generator.ui.tools

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import martabak.cult.generator.ui.layers.LayersState
import martabak.cult.generator.ui.tools.save.SaveMany
import martabak.cult.generator.ui.tools.save.SaveOne

@Composable
fun ToolsColumn(layersState: LayersState, modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        SaveOne(layersState)

        SaveMany(layersState)
    }
}