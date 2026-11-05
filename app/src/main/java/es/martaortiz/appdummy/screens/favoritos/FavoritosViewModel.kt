package es.martaortiz.appdummy.screens.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.martaortiz.appdummy.data.repository.LibrosRepository
import es.martaortiz.appdummy.data.model.Libro
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritosViewModel(
    repository: LibrosRepository
) : ViewModel() {

    val librosFavoritos: StateFlow<List<Libro>> =
        repository.libros
            .map { libros ->
                libros.filter { it.esFavorito }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )
}