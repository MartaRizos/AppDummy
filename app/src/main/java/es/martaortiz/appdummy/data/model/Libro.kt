package es.martaortiz.appdummy.data.model

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val year: Int?,
    val isbn: String,
    val cover: String,
    val esFavorito: Boolean = false,
    val leido: Boolean = false
)