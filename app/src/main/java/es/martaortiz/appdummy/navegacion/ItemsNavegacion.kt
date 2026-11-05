package es.martaortiz.appdummy.navegacion

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook

data class ItemNavegacion<T : Any>(
    val ruta: T,
    val titulo: String,
    val icono: ImageVector
)

val itemsNavegacion: List<ItemNavegacion<Any>> = listOf(
    ItemNavegacion(
        ruta = Listado,
        titulo = "Libros",
        icono = Icons.Default.MenuBook
    ),
    ItemNavegacion(
        ruta = Favoritos,
        titulo = "Favoritos",
        icono = Icons.Default.Favorite
    )
)