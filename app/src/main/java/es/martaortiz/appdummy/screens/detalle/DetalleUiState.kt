package es.martaortiz.appdummy.screens.detalle

import es.martaortiz.appdummy.data.model.Libro

sealed class DetalleUiState {
    data object Cargando : DetalleUiState()
    data class Exito(val libro: Libro) : DetalleUiState()
    data object NoEncontrado : DetalleUiState()
}