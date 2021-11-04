package martabak.cult.generator.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import martabak.cult.generator.metadata.MetadataReader

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MetadataView() {

    val reader = MetadataReader()
    reader.loadFromDirectory("/Users/bas/Desktop/BATCH/merged")
    val attr = reader.countAttributes()
    val rarity = reader.rarity(attr)
    println("")

    Box(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4)
        ) {
            items(rarity) {
                PropertyColumn(Pair(it.name, it.items), attr.get(it.name))
            }
        }
    }

}

@Composable
fun PropertyColumn(property: Pair<String, HashMap<Any, Double>>, attr: HashMap<Any, Int>?) {

    Column(Modifier.border(1.dp, Color.White).width(200.dp).height(300.dp).padding(8.dp)) {
        Text(property.first, Modifier.padding(bottom = 8.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold)

        property.second.forEach { (t, u) ->
            val f = "%.2f".format(u).toDouble()
            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("${t}", Modifier.weight(2f))
                Text("${attr?.get(t)}  |  ${f}%", Modifier.weight(1f))

            }
        }

    }

}