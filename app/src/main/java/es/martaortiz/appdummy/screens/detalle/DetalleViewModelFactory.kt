package es.martaortiz.appdummy.screens.detalle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.martaortiz.appdummy.data.repository.LibrosRepository

val detalleViewModelFactory: ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            val savedStateHandle = createSavedStateHandle()
            val repository = LibrosRepository()

            DetalleViewModel(
                savedStateHandle = savedStateHandle,
                repository = repository
            )
        }
    }