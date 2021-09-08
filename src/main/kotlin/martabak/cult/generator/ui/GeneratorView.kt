package martabak.cult.generator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
            LayersColumn(layerstate)
            CenterImage(layerstate)
            ToolsColumn(layerstate)
        }
    }
}

@Composable
fun CenterImage(layerState: LayersState) {

    layerState.layerList.forEach { l ->
        val image: BufferedImage =
            ImageIO.read(File(URLDecoder.decode("${l.path}${l.defaultDisplayName}/${l.selected}")))
        layerState.g.drawImage(image, 0, 0, 1800, 1800, null)

    }
    Image(layerState.c.toComposeBitmap(), "")

}
