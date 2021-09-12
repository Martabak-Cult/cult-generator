package martabak.cult.generator.ui.layers

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.squareup.moshi.Json
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.io.File
import kotlin.random.Random

class LayersState(default: List<LayerData> = arrayListOf()) {

    var layerList = mutableStateListOf<LayerData>()
    val c = BufferedImage(1800, 1800, BufferedImage.TYPE_INT_RGB)
    val g: Graphics = c.graphics

    val singleOutputPath = mutableStateOf(".")
    val manyOutputPath = mutableStateOf(".")
    val processing = mutableStateOf<Boolean>(false)

    init {
        default.forEach {
            loadLayer(it)
        }
    }

    fun loadLayer(data: LayerData) {
        val items = loadLayerItems("${data.path}${data.defaultDisplayName}")

        layerList.add(data.copy(items = items, selected = items.first()))
    }

    fun loadLayerItems(path: String): List<String> {
        return filesList(path)
    }

    fun filesList(path: String): List<String> {
        return File(path).list()!!.filter { !it.startsWith(".") }
    }

    fun nextItem(data: LayerData) {
        val currentIndex = data.items.indexOf(data.selected)
        val nextIndex = if (currentIndex + 1 >= data.items.size) 0 else currentIndex + 1
        val copyl =
            layerList.map { if (it.defaultDisplayName == data.defaultDisplayName) it.copy(selected = it.items[nextIndex]) else it }
                .toMutableList()
        layerList.clear()
        layerList.addAll(copyl)
    }

    fun prevItem(data: LayerData) {
        val currentIndex = data.items.indexOf(data.selected)
        val nextIndex = if (currentIndex - 1 < 0) data.items.size - 1 else currentIndex - 1
        val copyl =
            layerList.map { if (it.defaultDisplayName == data.defaultDisplayName) it.copy(selected = it.items[nextIndex]) else it }
                .toMutableList()
        layerList.clear()
        layerList.addAll(copyl)
    }

    fun shuffle(overrideState: Boolean = false): List<LayerData> {
        if (overrideState) {
            val copyl = layerList.map { it.copy(selected = it.items[Random.nextInt(it.items.size)]) }.toMutableList()
            layerList.clear()
            layerList.addAll(copyl)
            layerList
        }
        val copyl = layerList.map { it.copy(selected = it.items[Random.nextInt(it.items.size)]) }.toMutableList()
        return copyl
    }

    fun hideLayer(data: LayerData) {
        val copyl = layerList.map {if (data == it)  it.copy(paused = true) else it }.toMutableList()
        layerList.clear()
        layerList.addAll(copyl)
    }


    fun showLayer(data: LayerData) {
        val copyl = layerList.map { if (data == it) it.copy(paused = false) else it }.toMutableList()
        layerList.clear()
        layerList.addAll(copyl)
    }

    fun currentToMetadata(id: Int): Metadataa {
        val attributes = layerList.map { l -> Attribute(l.defaultDisplayName, l.selected.substringBefore('.')) }
        return Metadataa("Perso #$id", "descc", "", id, attributes)
    }

    data class LayerData(
        val path: String,
        val defaultDisplayName: String,
        val items: List<String> = arrayListOf(),
        val selected: String = "",
        val paused: Boolean = false
    )
}

data class Metadataa(
    val name: String,
    val description: String,
    val image: String,
    val tokenId: Int,
    val attributes: List<Attribute>
)

data class Attribute(
    @Json(name = "trait_type")
    val traitType: String,

    @Json(name = "value")
    val value: Any
)