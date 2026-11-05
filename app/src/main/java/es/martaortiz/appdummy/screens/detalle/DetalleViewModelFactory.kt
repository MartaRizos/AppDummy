package es.martaortiz.appdummy.screens.detalle

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.martaortiz.appdummy.data.AppContainer

val detalleViewModelFactory: ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            val savedStateHandle = createSavedStateHandle()

            DetalleViewModel(
                savedStateHandle = savedStateHandle,
                repository = AppContainer.librosRepository
            )
        }
    }