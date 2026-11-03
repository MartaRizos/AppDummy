package es.martaortiz.appdummy.screens.listado

import es.martaortiz.appdummy.data.model.Libro

sealed class LibrosUiState {

    data object Cargando : LibrosUiState()

    data class Exito(
        val libros: List<Libro>
    ) : LibrosUiState()

    data class Error(
        val mensaje: String
    ) : LibrosUiState()
}