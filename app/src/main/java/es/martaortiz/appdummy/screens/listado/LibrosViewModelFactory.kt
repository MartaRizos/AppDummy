package es.martaortiz.appdummy.screens.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.martaortiz.appdummy.data.repository.LibrosRepository

val librosViewModelFactory: ViewModelProvider.Factory =
    viewModelFactory {
        initializer {
            val repository = LibrosRepository()
            LibrosViewModel(repository)
        }
    }