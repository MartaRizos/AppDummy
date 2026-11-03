package es.martaortiz.appdummy.screens.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.martaortiz.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibrosViewModel(
    private val repository: LibrosRepository
) : ViewModel() {

    // Estado privado que puede modificarse desde el ViewModel
    private val _uiState = MutableStateFlow<LibrosUiState>(
        LibrosUiState.Cargando
    )

    // Estado público de solo lectura para la interfaz
    val uiState: StateFlow<LibrosUiState> = _uiState.asStateFlow()

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
}