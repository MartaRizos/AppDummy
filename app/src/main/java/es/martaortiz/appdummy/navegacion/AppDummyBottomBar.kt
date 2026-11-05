package es.martaortiz.appdummy.navegacion

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun <T : Any> AppDummyBottomBar(
    items: List<ItemNavegacion<T>>,
    rutaActual: T?,
    onNavegar: (T) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = {
                    onNavegar(item.ruta)
                },
                icon = {
                    Icon(
                        imageVector = item.icono,
                        contentDescription = item.titulo
                    )
                },
                label = {
                    Text(item.titulo)
                }
            )
        }
    }
}