package martabak.cult.generator.ui.tools.save

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import martabak.cult.generator.ui.layers.LayersState
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

@Composable
fun SaveOne(layersState: LayersState) {
    Button(onClick = {
        //isFileChooserOpen = true
        try {
            ImageIO.write(layersState.c, "jpg", File("test.jpg"))
        } catch (e: IOException) {
            print(e.printStackTrace())
        }
    }, Modifier.fillMaxWidth().padding(32.dp)) {
        Text(text = "Save")
    }
}