package martabak.cult.generator.metadata

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import martabak.cult.generator.ui.layers.Metadataa
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class MetadataReader {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val metadataList = arrayListOf<Metadataa>()

    fun loadFromFile(file: String) {

    }

    fun loadFromDirectory(dir: String) {
        val listFiles = File(dir).list()!!.filter { !it.startsWith(".") }.filter { it.endsWith(".json") }

        listFiles.forEach { f ->
            val metadata =
                moshi.adapter(Metadataa::class.java).fromJson(BufferedReader(FileReader("${dir}/${f}")).readText())
            metadataList.add(metadata!!)
        }
    }

    fun countAttributes(): Map<String, HashMap<Any, Int>> {

        val attrMap = hashMapOf<String, HashMap<Any, Int>>()

        metadataList.forEach { meta ->
            meta.attributes.forEach { attr ->
                attrMap.putIfAbsent(attr.traitType, hashMapOf<Any, Int>())
                attrMap.get(attr.traitType)?.putIfAbsent(attr.value, 0)
                val curr = attrMap[attr.traitType]?.getValue(attr.value)!!
                attrMap[attr.traitType]?.set(attr.value, curr.plus(1))
            }
        }
        return attrMap
    }

    fun rarity(attributesCount: Map<String, HashMap<Any, Int>>): List<Rarity> {
        val attrMap = hashMapOf<String, HashMap<Any, Double>>()

        attributesCount.forEach { (type, items) ->
            items.forEach { (name, count) ->
                attrMap.putIfAbsent(type, hashMapOf<Any, Double>())
                val percent = count.toDouble().div(metadataList.size.toDouble()).times(100)
                attrMap.get(type)?.putIfAbsent(name, percent)
            }
        }
        val final = arrayListOf<Rarity>()
        attrMap.forEach { (t, u) ->
            final.add(Rarity(t, u))
        }

        return final
    }
}

data class Rarity(
    val name: String,
    val items: HashMap<Any, Double>
)