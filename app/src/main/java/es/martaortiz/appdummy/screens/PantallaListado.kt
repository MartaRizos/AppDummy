package es.martaortiz.appdummy.screens

import android.content.Intent
import android.util.Patterns
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import es.martaortiz.appdummy.R

// Modelo de datos simple
data class LibroUI(
    val id: Int = 0,
    val titulo: String = "",
    val autor: String = "",
    val year: Int? = 1900,
    val isbn: String = "",
    val cover: String = "",
    val esFavorito: Boolean = false,
    val leido: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListado() {

    // Estado local de la pantalla
    var busqueda by remember { mutableStateOf("") }
    var autorSeleccionado by remember { mutableStateOf("Todos") }

    var libros by remember {
        mutableStateOf(
            listOf(
                LibroUI(
                    id = 1,
                    titulo = "Proyecto Hail Mary",
                    autor = "Andy Weir",
                    year = 2021,
                    isbn = "9788418037016",
                    cover = "https://covers.openlibrary.org/b/isbn/9788418037016-L.jpg",
                    esFavorito = true,
                    leido = false
                ),
                LibroUI(
                    id = 2,
                    titulo = "Juego de tronos",
                    autor = "George R.R. Martin",
                    year = 1996,
                    isbn = "9780307951182",
                    cover = "https://covers.openlibrary.org/b/isbn/9780307951182-L.jpg",
                    esFavorito = true,
                    leido = true
                ),
                LibroUI(
                    id = 3,
                    titulo = "Festín de cuervos",
                    autor = "George R.R. Martin",
                    year = 2005,
                    isbn = "9780307951212",
                    cover = "https://covers.openlibrary.org/b/isbn/9780307951212-L.jpg",
                    esFavorito = false,
                    leido = false
                ),
                LibroUI(
                    id = 4,
                    titulo = "Cementerio de Animales",
                    autor = "Stephen King",
                    year = 1983,
                    isbn = "9788401499845",
                    cover = "https://covers.openlibrary.org/b/isbn/9788401499845-L.jpg",
                    esFavorito = false,
                    leido = true
                ),
                LibroUI(
                    id = 5,
                    titulo = "El juego de Ender",
                    autor = "Orson Scott Card",
                    year = 1985,
                    isbn = "9788498720068",
                    cover = "https://covers.openlibrary.org/b/isbn/9788498720068-L.jpg",
                    esFavorito = false,
                    leido = true
                )
            )
        )
    }

    // Lista de autores para los filtros
    val autores = listOf("Todos") +
            libros.map { it.autor }.distinct().sorted()

    // Filtrado de libros por título y autor
    val librosFiltrados = libros.filter { libro ->

        val coincideBusqueda =
            busqueda.isBlank() ||
                    libro.titulo.contains(busqueda, ignoreCase = true)

        val coincideAutor =
            autorSeleccionado == "Todos" ||
                    libro.autor == autorSeleccionado

        coincideBusqueda && coincideAutor
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("AppDummy")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Perfil"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                placeholder = {
                    Text("Buscar libros...")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = busqueda.isNotEmpty()
                    ) {
                        IconButton(
                            onClick = { busqueda = "" }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Borrar búsqueda"
                            )
                        }
                    }
                },
                singleLine = true
            )

            // Filtros por autor
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(autores) { autor ->

                    FilterChip(
                        selected = autor == autorSeleccionado,
                        onClick = {
                            autorSeleccionado = autor
                        },
                        label = {
                            Text(autor)
                        }
                    )
                }
            }

            // Resultados de la búsqueda
            if (librosFiltrados.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Sin resultados para \"$busqueda\"",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

            } else {

                // Cuadrícula de libros
                LazyVerticalGrid(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(
                        items = librosFiltrados,
                        key = { it.id }
                    ) { libro ->

                        ItemLibro(
                            libro = libro,

                            onToggleLeido = { id ->
                                libros = libros.map {
                                    if (it.id == id) {
                                        it.copy(leido = !it.leido)
                                    } else {
                                        it
                                    }
                                }
                            },

                            onToggleFavorito = { id ->
                                libros = libros.map {
                                    if (it.id == id) {
                                        it.copy(esFavorito = !it.esFavorito)
                                    } else {
                                        it
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLibro(
    libro: LibroUI,
    onToggleLeido: (Int) -> Unit,
    onToggleFavorito: (Int) -> Unit
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Portada del libro
            if (Patterns.WEB_URL.matcher(libro.cover).matches()) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(libro.cover)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Portada de ${libro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )

            } else {

                AsyncImage(
                    model = R.drawable.nocover,
                    contentDescription = "Portada de ${libro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Título del libro
            Text(
                text = libro.titulo,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            // Autor y año de publicación
            Text(
                text = "${libro.autor} • ${libro.year ?: "Año desconocido"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Botones de leído, favorito y compartir
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Botón para marcar como leído
                IconButton(
                    onClick = {
                        onToggleLeido(libro.id)
                    }
                ) {

                    Icon(
                        imageVector = if (libro.leido) {
                            Icons.Default.BookmarkAdded
                        } else {
                            Icons.Default.BookmarkBorder
                        },
                        contentDescription = if (libro.leido) {
                            "Quitar leído"
                        } else {
                            "Marcar como leído"
                        },
                        tint = if (libro.leido) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                // Botón para marcar como favorito
                IconButton(
                    onClick = {
                        onToggleFavorito(libro.id)
                    }
                ) {

                    Icon(
                        imageVector = if (libro.esFavorito) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = if (libro.esFavorito) {
                            "Quitar favorito"
                        } else {
                            "Añadir favorito"
                        },
                        tint = if (libro.esFavorito) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                // Botón para compartir el libro
                IconButton(
                    onClick = {

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"

                            putExtra(
                                Intent.EXTRA_TEXT,
                                "${libro.titulo} - ${libro.autor}"
                            )
                        }

                        context.startActivity(
                            Intent.createChooser(
                                intent,
                                "Compartir libro"
                            )
                        )
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir libro",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Vista previa de la pantalla completa
@Preview(showBackground = true)
@Composable
fun PantallaListadoPreview() {

    MaterialTheme {
        PantallaListado()
    }
}

// Vista previa de una tarjeta individual
@Preview(showBackground = true)
@Composable
fun ItemLibroPreview() {

    MaterialTheme {
        ItemLibro(
            libro = LibroUI(
                id = 1,
                titulo = "Proyecto Hail Mary",
                autor = "Andy Weir",
                year = 2021,
                isbn = "9788418037016",
                cover = "",
                esFavorito = true,
                leido = false
            ),
            onToggleLeido = { },
            onToggleFavorito = { }
        )
    }
}