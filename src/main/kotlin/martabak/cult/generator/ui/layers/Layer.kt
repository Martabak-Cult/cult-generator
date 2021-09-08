package martabak.cult.generator.ui.layers

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Layer(dir: LayersState.LayerData, layerstate: LayersState) {
    Column(
        Modifier.fillMaxWidth().padding(top = 16.dp),
    ) {
        Text(dir.defaultDisplayName, Modifier.fillMaxWidth(), fontSize = 16.sp, textAlign = TextAlign.Center)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton( onClick = {
                layerstate.prevItem(dir)
            }, Modifier.weight(1f)) {
                Icon(
                    Icons.Filled.ArrowLeft,
                    "contentDescription",
                    tint = Color.White
                )
            }
            Text(dir.selected.substringBefore('.'),Modifier.weight(3f), fontSize = 12.sp, textAlign = TextAlign.Center)
            IconButton(onClick = {
                layerstate.nextItem(dir)
            }, Modifier.weight(1f)) {
                Icon(
                    Icons.Filled.ArrowRight,
                    "contentDescription2",
                    tint = Color.White
                )
            }
        }
    }
}