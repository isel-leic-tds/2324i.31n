import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var text by mutableStateOf("Hello, World!")
    var text2 by remember { mutableStateOf("Hello, World!") }
    println("App")
    MaterialTheme {
        Column {
            Row {
                Button(onClick = {
                    text = "Hello, Desktop!"
                }) {
                    println("Button")
                    Text(text)
                }
            }
            Row {
                Button(onClick = {
                    text2 = "Hello, Desktop!"
                }) {
                    println("Button")
                    Text(text2)

                }
            }
            Row {
                Button(onClick = {
                    text = "Hello, Desktop!"
                }) {
                    println("Button")
                    Text(text)

                }
            }
        }
    }
}

fun main() =
    application {
        Window(onCloseRequest = ::exitApplication) {
            App()
        }
    }
