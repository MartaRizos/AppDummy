package es.martaortiz.appdummy.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import es.martaortiz.appdummy.screens.bienvenida.PantallaBienvenida
import es.martaortiz.appdummy.screens.listado.PantallaListado

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

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
    }
}