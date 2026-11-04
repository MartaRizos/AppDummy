package es.martaortiz.appdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import es.martaortiz.appdummy.navegacion.AppNavigation
import es.martaortiz.appdummy.ui.theme.AppDummyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppDummyTheme {
                AppNavigation()
            }
        }
    }
}