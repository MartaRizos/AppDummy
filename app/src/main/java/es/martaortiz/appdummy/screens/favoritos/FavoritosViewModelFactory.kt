package es.martaortiz.appdummy.screens.favoritos

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.martaortiz.appdummy.data.AppContainer

val favoritosViewModelFactory: ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            FavoritosViewModel(
                repository = AppContainer.librosRepository
            )
        }
    }