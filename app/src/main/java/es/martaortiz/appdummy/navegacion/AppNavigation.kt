package es.martaortiz.appdummy.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hasRoute
import es.martaortiz.appdummy.screens.bienvenida.PantallaBienvenida
import es.martaortiz.appdummy.screens.listado.PantallaListado
import es.martaortiz.appdummy.screens.detalle.PantallaDetalle
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            val mostrarBottomBar =
                rutaActual?.hasRoute<Listado>() == true ||
                        rutaActual?.hasRoute<Favoritos>() == true

            if (mostrarBottomBar) {
                AppDummyBottomBar<Any>(
                    items = itemsNavegacion,
                    rutaActual = when {
                        rutaActual?.hasRoute<Listado>() == true -> Listado
                        rutaActual?.hasRoute<Favoritos>() == true -> Favoritos
                        else -> null
                    },
                    onNavegar = { ruta ->
                        navController.navigate(ruta)
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Inicio,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<Inicio> {
                PantallaBienvenida(
                    onEntrar = {
                        navController.navigate(Listado) {
                            popUpTo<Inicio> {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<Listado> {
                PantallaListado(
                    onNavegaADetalle = { id ->
                        navController.navigate(Detalle(id))
                    },
                    onNavegaAPermisos = {
                        navController.navigate(Permisos)
                    }
                )
            }

            composable<Detalle> {
                PantallaDetalle(
                    onVolver = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}