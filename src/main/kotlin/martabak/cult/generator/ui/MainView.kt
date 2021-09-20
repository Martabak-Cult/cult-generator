package martabak.cult.generator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import martabak.cult.generator.ui.theme.AppTheme
import martabak.cult.generator.ui.theme.PlatformTheme

enum class Tabs(val title: String) {
    Tab1("Generator"),
    Tab2("Metadata")
}

@Composable
fun MainView() {
    var tab by mutableStateOf(Tabs.Tab1)

    DisableSelection {
        MaterialTheme(
            colors = AppTheme.colors.material
        ) {
            PlatformTheme {
                Surface {
                    Column {
                        Row(Modifier.height(54.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            TextButton(onClick = {
                                tab = Tabs.Tab1
                            }, Modifier.padding(start = 16.dp)) {
                                Text("Generator")
                            }
                            TextButton(onClick = {
                                tab = Tabs.Tab2
                            }, Modifier.padding(start = 16.dp)) {
                                Text("Metadata")
                            }
                        }
                        when (tab) {
                            Tabs.Tab1 -> GeneratorView()
                            Tabs.Tab2 -> MetadataView()
                        }
                    }
                }
            }
        }
    }
}