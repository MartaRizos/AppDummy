package es.martaortiz.appdummy.screens.detalle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import es.martaortiz.appdummy.data.repository.LibrosRepository
import es.martaortiz.appdummy.navegacion.Detalle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: LibrosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetalleUiState>(
        DetalleUiState.Cargando
    )
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    init {
        val ruta = savedStateHandle.toRoute<Detalle>()
        cargarLibro(ruta.id)
    }

    private fun cargarLibro(id: Int) {
        viewModelScope.launch {
            val libro = repository.obtenerLibros()
                .find { it.id == id }

            _uiState.value = if (libro != null) {
                DetalleUiState.Exito(libro)
            } else {
                DetalleUiState.NoEncontrado
            }
        }
    }
}