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
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import martabak.cult.generator.ui.layers.Attribute
import martabak.cult.generator.ui.layers.LayersState
import martabak.cult.generator.ui.layers.Metadataa
import martabak.cult.generator.ui.utils.FileDialog
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.io.PrintStream
import java.net.URLDecoder
import javax.imageio.ImageIO

@Composable
fun SaveOne(layersState: LayersState) {

    var pathChooserOpen by remember { mutableStateOf(false) }
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
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
        Text("Single")
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("Output path", Modifier.weight(3f).padding(top = 8.dp), fontSize = 14.sp)
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

            val id =  (File(layersState.singleOutputPath.value).list().size / 2) + 1

            try {
                val path = layersState.singleOutputPath.value + "/${id}.jpg"
                ImageIO.write(layersState.c, "jpg", File(path))
            } catch (e: IOException) {
                print(e.printStackTrace())
            }



            val c = BufferedImage(1800, 1800, BufferedImage.TYPE_INT_RGB)
            val g: Graphics = c.graphics
            val current = layersState.current()
            current.forEach { l ->
                val image: BufferedImage =
                    ImageIO.read(File(URLDecoder.decode("${l.path}${l.defaultDisplayName}/${l.selected}")))
                g.drawImage(image, 0, 0, 1800, 1800, null)
            }

            try {
                val path = layersState.manyOutputPath.value + "/${id}.jpg"
                ImageIO.write(c, "jpg", File(path))
                c.flush()
            } catch (e: IOException) {
                print(e.printStackTrace())
            }

            val attributes =
                current.map { l ->
                    var cleanAttr = l.selected
                    if (cleanAttr.startsWith("empty")) {
                        cleanAttr = "empty.png"
                    }

                    if (cleanAttr.startsWith("_")){
                        cleanAttr = cleanAttr.replace("_", "")
                    }
                    Attribute(l.defaultDisplayName, cleanAttr.substringBefore('.'))
                }
            val filtered =  attributes.filter { it.value != "empty" }
            val metadata = Metadataa("Martabak Cult #${id}", "descc", "", id, filtered)

            val json = moshi.adapter(Metadataa::class.java).toJson(metadata)
            PrintStream(layersState.singleOutputPath.value + "/${id}.json").use { ps -> ps.println(json) }



        }, Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text(text = "Save")
        }
    }

}