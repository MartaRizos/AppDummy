package es.martaortiz.appdummy.screens.listado

import es.martaortiz.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LibrosViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LibrosViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = LibrosViewModel(LibrosRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun cargarLibros_devuelveCincoLibros() = runTest(dispatcher) {

        advanceUntilIdle()

        val estado = viewModel.uiState.value

        assertTrue(estado is LibrosUiState.Exito)

        val libros = (estado as LibrosUiState.Exito).libros

        assertEquals(5, libros.size)
    }

    @Test
    fun alternarFavorito_modificaEstadoDelLibro() = runTest(dispatcher) {

        advanceUntilIdle()

        viewModel.alternarFavorito(1)

        val estado = viewModel.uiState.value

        assertTrue(estado is LibrosUiState.Exito)

        val libros = (estado as LibrosUiState.Exito).libros
        val libro = libros.find { it.id == 1 }

        assertEquals(false, libro?.esFavorito)
    }
}