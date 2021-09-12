package martabak.cult.generator.ui.tools.save

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
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
fun SaveMany(layersState: LayersState) {

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    var pathChooserOpen by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf(100) }
    var genCount by remember { mutableStateOf(0) }
    val composableScope = rememberCoroutineScope()

    if (pathChooserOpen) {
        FileDialog(
            onCloseRequest = { path, file ->
                pathChooserOpen = false
                if (path != null && file != null) {
                    layersState.manyOutputPath.value = "${path}${file}"
                }
            }
        )
    }

    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Batch")
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
        Text(layersState.manyOutputPath.value, Modifier.padding(top = 4.dp), fontSize = 10.sp)

        TextField(amount.toString(), onValueChange = {
            amount = it.toInt()
        }, Modifier.padding(top = 16.dp))

        Button(onClick = {
            layersState.processing.value = true
            val requestSemaphore = Semaphore(10)
            for (i in 1..amount) {
                composableScope.launch(Dispatchers.IO) {
                    requestSemaphore.withPermit {
                        genCount++

                        val c = BufferedImage(1800, 1800, BufferedImage.TYPE_INT_RGB)
                        val g: Graphics = c.graphics
                        val current = layersState.shuffle()
                        current.forEach { l ->
                            val image: BufferedImage =
                                ImageIO.read(File(URLDecoder.decode("${l.path}${l.defaultDisplayName}/${l.selected}")))
                            g.drawImage(image, 0, 0, 1800, 1800, null)
                        }

                        try {
                            val path = layersState.manyOutputPath.value + "/$i.jpg"
                            ImageIO.write(c, "jpg", File(path))
                            c.flush()
                        } catch (e: IOException) {
                            print(e.printStackTrace())
                        }

                        val attributes =
                            current.map { l -> Attribute(l.defaultDisplayName, l.selected.substringBefore('.')) }
                        val metadata = Metadataa("Perso #$i", "descc", "", i, attributes)

                        val json = moshi.adapter(Metadataa::class.java).toJson(metadata)
                        PrintStream(layersState.manyOutputPath.value + "/$i.json").use { ps -> ps.println(json) }
                    }
                }
            }
            layersState.processing.value = false


        }, Modifier.fillMaxWidth().padding(top = 16.dp)) {

            Text(text = "Generate $genCount/$amount")
        }
    }

}