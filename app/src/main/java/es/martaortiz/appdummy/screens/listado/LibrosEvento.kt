package es.martaortiz.appdummy.screens.listado

sealed class LibrosEvento {

    data class MostrarMensaje(
        val mensaje: String
    ) : LibrosEvento()

}