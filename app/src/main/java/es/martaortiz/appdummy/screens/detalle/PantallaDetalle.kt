package es.martaortiz.appdummy.screens.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.foundation.layout.size

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    onVolver: () -> Unit,
    viewModel: DetalleViewModel = viewModel(
        factory = detalleViewModelFactory
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del libro") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when (uiState) {
                is DetalleUiState.Cargando -> {
                    CircularProgressIndicator()
                }

                is DetalleUiState.Exito -> {
                    val libro = (uiState as DetalleUiState.Exito).libro

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = libro.cover,
                            contentDescription = "Portada de ${libro.titulo}",
                            modifier = Modifier.size(
                                width = 180.dp,
                                height = 260.dp
                            )
                        )

                        Text(
                            text = libro.titulo,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = "Autor: ${libro.autor}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Año: ${libro.year ?: "Desconocido"}"
                        )

                        Text(
                            text = "ISBN: ${libro.isbn}"
                        )

                        Text(
                            text = if (libro.esFavorito) "★ Favorito" else "☆ No favorito"
                        )

                        Text(
                            text = if (libro.leido) "Leído" else "Pendiente de lectura"
                        )
                    }
                }

                is DetalleUiState.NoEncontrado -> {
                    Text(
                        text = "No se ha encontrado el libro",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}