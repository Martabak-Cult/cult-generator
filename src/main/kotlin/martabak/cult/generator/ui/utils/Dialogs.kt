package martabak.cult.generator.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.AwtWindow
import java.awt.FileDialog
import java.awt.Frame


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FileDialog(
    parent: Frame? = null,
    onCloseRequest: (path: String?, name: String?) -> Unit
) = AwtWindow(
    create = {
        System.setProperty("apple.awt.fileDialogForDirectories", "true");

        object : FileDialog(parent, "Choose a file", LOAD) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    onCloseRequest(directory, file)
                }
            }
        }
    },
    dispose = FileDialog::dispose
)