package martabak.cult.generator.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import martabak.cult.generator.ui.layers.Layer
import martabak.cult.generator.ui.layers.LayersState
import martabak.cult.generator.ui.utils.FileDialog

@Composable
fun LayersColumn(layerState: LayersState, modifier: Modifier = Modifier) {

    var layerChooserOpen by remember { mutableStateOf(false) }
    if (layerChooserOpen) {
        FileDialog(
            onCloseRequest = { path, file ->
                layerChooserOpen = false
                if (path != null && file != null) {
                    layerState.loadLayer(LayersState.LayerData(path, file))
                }
            }
        )
    }

    Column(modifier = modifier) {
        Text("LAYERS", Modifier.fillMaxWidth().padding(32.dp), textAlign = TextAlign.Center)
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            layerState.layerList.forEach { layer ->
                Layer(layer, layerState)
                Divider(color = Color.White)
            }
        }

        Button(onClick = {
            layerChooserOpen = true
        }, Modifier.fillMaxWidth().padding(32.dp)) {
            Text(text = "Add Layer")
        }
    }

}