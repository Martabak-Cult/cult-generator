package martabak.cult.generator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeBitmap
import martabak.cult.generator.ui.layers.LayersState
import martabak.cult.generator.ui.tools.ToolsColumn
import java.awt.image.BufferedImage
import java.io.File
import java.net.URLDecoder
import javax.imageio.ImageIO

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun GeneratorView() {

    var layerstate = remember {
        LayersState(
            arrayListOf(
                LayersState.LayerData("/Users/bas/Desktop/METANATOR/", "Background"),
                LayersState.LayerData("/Users/bas/Desktop/METANATOR/", "Body"),
                LayersState.LayerData("/Users/bas/Desktop/METANATOR/", "Shirt"),
                LayersState.LayerData("/Users/bas/Desktop/METANATOR/", "Head"),
                LayersState.LayerData("/Users/bas/Desktop/METANATOR/", "Eyes"),
                LayersState.LayerData("/Users/bas/Desktop/METANATOR/", "Mouth"),
            )
        )
    }

    Box {
        Row(Modifier.fillMaxSize()) {
            LayersColumn(layerstate, Modifier.weight(1f).fillMaxHeight().fillMaxWidth())
            CenterImage(layerstate, Modifier.wrapContentWidth().background(Color.Red))
            ToolsColumn(layerstate, Modifier.weight(1f).fillMaxHeight().fillMaxWidth())
        }
    }
}

@Composable
fun CenterImage(layerState: LayersState, modifier: Modifier = Modifier) {

    layerState.layerList.forEach { l ->
        if (!l.paused) {
            val image: BufferedImage =
                ImageIO.read(File(URLDecoder.decode("${l.path}${l.defaultDisplayName}/${l.selected}")))
            layerState.g.drawImage(image, 0, 0, 1800, 1800, null)
        }
    }
    Image(layerState.c.toComposeBitmap(), "", modifier = modifier)

}
