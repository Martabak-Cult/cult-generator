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
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Background"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Body"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Head"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Hair"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Eyes"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Shirt"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Neck"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Accessory"),
                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Gynoid/", "Hands"),


            //ANDDROID
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Background"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Body"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Head"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Hair"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Eyes"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Shirt"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Neck"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Accessory"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Android/", "Hands"),


                //SHAMAN LAYERS
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Background"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Head"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Eyes"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Body"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Neck"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Left hand"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Hands"),
//                LayersState.LayerData("/Users/bas/Desktop/CULTFINAL/Shaman/", "Right hand"),
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
