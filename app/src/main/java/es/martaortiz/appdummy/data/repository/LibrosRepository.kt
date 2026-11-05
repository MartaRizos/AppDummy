package es.martaortiz.appdummy.data.repository

import es.martaortiz.appdummy.data.model.Libro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibrosRepository {

    // Lista única de libros conservada en memoria
    private val _libros = MutableStateFlow(
listOf(
            Libro(
                id = 1,
                titulo = "Proyecto Hail Mary",
                autor = "Andy Weir",
                year = 2021,
                isbn = "9788418037016",
                cover = "https://covers.openlibrary.org/b/isbn/9788418037016-L.jpg",
                esFavorito = true
            ),
            Libro(
                id = 2,
                titulo = "Juego de tronos",
                autor = "George R.R. Martin",
                year = 1996,
                isbn = "9780307951182",
                cover = "https://covers.openlibrary.org/b/isbn/9780307951182-L.jpg",
                esFavorito = true,
                leido = true
            ),
            Libro(
                id = 3,
                titulo = "Festín de cuervos",
                autor = "George R.R. Martin",
                year = 2005,
                isbn = "9780307951212",
                cover = "https://covers.openlibrary.org/b/isbn/9780307951212-L.jpg"
            ),
            Libro(
                id = 4,
                titulo = "Cementerio de Animales",
                autor = "Stephen King",
                year = 1983,
                isbn = "9788401499845",
                cover = "https://covers.openlibrary.org/b/isbn/9788401499845-L.jpg",
                leido = true
            ),
            Libro(
                id = 5,
                titulo = "El juego de Ender",
                autor = "Orson Scott Card",
                year = 1985,
                isbn = "9788498720068",
                cover = "https://covers.openlibrary.org/b/isbn/9788498720068-L.jpg",
                leido = true
            )
        )
    )

    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    fun obtenerLibros(): List<Libro> {
        return _libros.value
    }

    fun obtenerLibroPorId(id: Int): Libro? {
        return _libros.value.find { it.id == id }
    }

    fun actualizarLibro(libroActualizado: Libro) {
        _libros.value = _libros.value.map { libro ->
            if (libro.id == libroActualizado.id) {
                libroActualizado
            } else {
                libro
            }
        }
    }
}