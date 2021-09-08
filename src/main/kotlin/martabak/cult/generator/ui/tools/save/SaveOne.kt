package martabak.cult.generator.ui.tools.save

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import martabak.cult.generator.ui.layers.LayersState
import martabak.cult.generator.ui.utils.FileDialog
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

@Composable
fun SaveOne(layersState: LayersState) {

    var pathChooserOpen by remember { mutableStateOf(false) }

    if (pathChooserOpen) {
        FileDialog(
            onCloseRequest = { path, file ->
                pathChooserOpen = false
                if (path != null && file != null) {
                    layersState.singleOutputPath.value = "${path}${file}"
                }
            }
        )
    }

    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("Output path", Modifier.weight(3f))
            IconButton(onClick = {
                pathChooserOpen = true
            }, Modifier.size(16.dp).weight(1f)) {
                Icon(
                    Icons.Filled.Settings,
                    "settings",
                    tint = Color.White
                )
            }
        }
        Text(layersState.singleOutputPath.value, Modifier.padding(top = 4.dp), fontSize = 10.sp)
        Button(onClick = {
            try {
                val path = layersState.singleOutputPath.value + "/test.jpg"
                ImageIO.write(layersState.c, "jpg", File(path))
            } catch (e: IOException) {
                print(e.printStackTrace())
            }
        }, Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text(text = "Save")
        }
    }

}