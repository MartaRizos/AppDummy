package es.martaortiz.appdummy.screens.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.martaortiz.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class LibrosViewModel(
    private val repository: LibrosRepository
) : ViewModel() {

    // Estado privado que puede modificarse desde el ViewModel
    private val _uiState = MutableStateFlow<LibrosUiState>(
        LibrosUiState.Cargando
    )
    // Estado público de solo lectura para la interfaz
    val uiState: StateFlow<LibrosUiState> = _uiState.asStateFlow()

    private val _eventos = MutableSharedFlow<LibrosEvento>()

    val eventos: SharedFlow<LibrosEvento> = _eventos.asSharedFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _autorSeleccionado = MutableStateFlow("Todos")
    val autorSeleccionado: StateFlow<String> =
        _autorSeleccionado.asStateFlow()

    val librosFiltrados: StateFlow<List<es.martaortiz.appdummy.data.model.Libro>> =
        combine(
            uiState,
            busqueda,
            autorSeleccionado
        ) { estado, textoBusqueda, autor ->

            if (estado is LibrosUiState.Exito) {
                estado.libros.filter { libro ->

                    val coincideTitulo = libro.titulo.contains(
                        textoBusqueda,
                        ignoreCase = true
                    )

                    val coincideAutor = autor == "Todos" ||
                            libro.autor == autor

                    coincideTitulo && coincideAutor
                }
            } else {
                emptyList()
            }

        }.let { flujo ->
            flujo.stateIn(
                viewModelScope,
                kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
        }

    init {
        cargarLibros()
    }

    private fun cargarLibros() {
        viewModelScope.launch {
            try {
                val libros = repository.obtenerLibros()
                _uiState.value = LibrosUiState.Exito(libros)
            } catch (e: Exception) {
                _uiState.value = LibrosUiState.Error(
                    e.message ?: "Error al cargar los libros"
                )
            }
        }
    }

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    fun seleccionarAutor(autor: String) {
        _autorSeleccionado.value = autor
    }

    fun alternarFavorito(id: Int) {
        val estadoActual = _uiState.value

        if (estadoActual is LibrosUiState.Exito) {
            val libro = estadoActual.libros.find { it.id == id }

            if (libro != null) {
                val nuevoEstado = !libro.esFavorito

                actualizarLibro(id) { libroActual ->
                    libroActual.copy(esFavorito = nuevoEstado)
                }

                viewModelScope.launch {
                    val mensaje = if (nuevoEstado) {
                        "Libro añadido a favoritos"
                    } else {
                        "Libro eliminado de favoritos"
                    }

                    _eventos.emit(
                        LibrosEvento.MostrarMensaje(mensaje)
                    )
                }
            }
        }
    }

    fun alternarLeido(id: Int) {
        actualizarLibro(id) { libro ->
            libro.copy(leido = !libro.leido)
        }
    }

    private fun actualizarLibro(
        id: Int,
        actualizar: (es.martaortiz.appdummy.data.model.Libro) ->
        es.martaortiz.appdummy.data.model.Libro
    ) {
        val estadoActual = _uiState.value

        if (estadoActual is LibrosUiState.Exito) {
            val librosActualizados = estadoActual.libros.map { libro ->
                if (libro.id == id) actualizar(libro) else libro
            }

            _uiState.value = LibrosUiState.Exito(librosActualizados)
        }
    }
}