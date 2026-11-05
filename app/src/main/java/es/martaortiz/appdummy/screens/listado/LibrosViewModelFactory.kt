package es.martaortiz.appdummy.screens.listado

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.martaortiz.appdummy.data.AppContainer

val librosViewModelFactory: ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            LibrosViewModel(
                AppContainer.librosRepository
            )
        }
    }