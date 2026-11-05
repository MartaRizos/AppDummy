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
                AppDummyBottomBar(
                    items = itemsNavegacion,
                    rutaActual = null,
                    onNavegar = {}
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Inicio
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