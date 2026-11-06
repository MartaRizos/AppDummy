package es.martaortiz.appdummy.screens.listado

import android.content.Intent
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import es.martaortiz.appdummy.data.model.Libro
import es.martaortiz.appdummy.data.repository.LibrosRepository
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridItemSpan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListado(
    onNavegaADetalle: (Int) -> Unit,
    onNavegaAPermisos: () -> Unit,
    viewModel: LibrosViewModel = viewModel(
        factory = librosViewModelFactory
    )
) {

    // Observar los estados del ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val busqueda by viewModel.busqueda.collectAsStateWithLifecycle()

    val autorSeleccionado by
    viewModel.autorSeleccionado.collectAsStateWithLifecycle()

    val librosFiltrados by
    viewModel.librosFiltrados.collectAsStateWithLifecycle()

    val autores = listOf("Todos") +
            (uiState as? LibrosUiState.Exito)
                ?.libros
                ?.map { it.autor }
                ?.distinct()
                ?.sorted()
                .orEmpty()

// Estado del Snackbar
    val snackbarHostState = remember {
        SnackbarHostState()
    }

// Recoger eventos del ViewModel
    LaunchedEffect(viewModel) {
        viewModel.eventos.collect { evento ->
            when (evento) {
                is LibrosEvento.MostrarMensaje -> {
                    snackbarHostState.showSnackbar(evento.mensaje)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("AppDummy")
                },
                actions = {
                    IconButton(onClick = onNavegaAPermisos) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Gestión de permisos"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {

                is LibrosUiState.Cargando -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is LibrosUiState.Error -> {
                    val mensaje = (uiState as LibrosUiState.Error).mensaje

                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is LibrosUiState.Exito -> {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        // Buscador
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            OutlinedTextField(
                                value = busqueda,
                                onValueChange = {
                                    viewModel.actualizarBusqueda(it)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
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
                                            onClick = {
                                                viewModel.actualizarBusqueda("")
                                            }
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
                        }

                        // Filtros por autor
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(autores) { autor ->
                                    FilterChip(
                                        selected = autor == autorSeleccionado,
                                        onClick = {
                                            viewModel.seleccionarAutor(autor)
                                        },
                                        label = {
                                            Text(autor)
                                        }
                                    )
                                }
                            }
                        }

                        // Mensaje cuando no hay resultados
                        if (librosFiltrados.isEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SearchOff,
                                        contentDescription = null,
                                        modifier = Modifier.size(64.dp),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "Sin resultados para \"$busqueda\"",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        } else {

                            items(
                                items = librosFiltrados,
                                key = { it.id }
                            ) { libro ->
                                ItemLibro(
                                    libro = libro,
                                    onClick = {
                                        onNavegaADetalle(libro.id)
                                    },
                                    onToggleLeido = { id ->
                                        viewModel.alternarLeido(id)
                                    },
                                    onToggleFavorito = { id ->
                                        viewModel.alternarFavorito(id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLibro(
    libro: Libro,
    onClick: () -> Unit,
    onToggleLeido: (Int) -> Unit,
    onToggleFavorito: (Int) -> Unit
) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Portada del libro
            if (libro.cover.isNotBlank()) {

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
    PantallaListado(
        onNavegaADetalle = {},
        onNavegaAPermisos = {}
    )
}

// Vista previa de una tarjeta individual
@Preview(showBackground = true)
@Composable
fun ItemLibroPreview() {

    MaterialTheme {
        ItemLibro(
            libro = Libro(
                id = 1,
                titulo = "Proyecto Hail Mary",
                autor = "Andy Weir",
                year = 2021,
                isbn = "9788418037016",
                cover = "",
                esFavorito = true,
                leido = false
            ),
            onClick = {},
            onToggleLeido = {},
            onToggleFavorito = {}
        )
    }
}