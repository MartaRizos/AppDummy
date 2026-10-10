
package es.martaortiz.appdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import es.martaortiz.appdummy.screens.PantallaListado
import es.martaortiz.appdummy.screens.PantallaGestionPermisos
import es.martaortiz.appdummy.ui.theme.AppDummyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppDummyTheme {

                // Cambio este valor según la pantalla a mostrar:
                val pantalla = 2

                when (pantalla) {
                    1 -> PantallaBienvenida(
                        onEntrar = { }
                    )

                    2 -> PantallaListado()

                    3 -> PantallaGestionPermisos()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppDummyTheme {
        Greeting("Android")
    }
}


