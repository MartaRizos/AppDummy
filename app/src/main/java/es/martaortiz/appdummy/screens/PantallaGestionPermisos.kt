package es.martaortiz.appdummy.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.material.icons.filled.Share

// Estados posibles del permiso de cámara
sealed class EstadoPermiso {
    data object Concedido : EstadoPermiso()
    data object Pendiente : EstadoPermiso()
    data object Denegado : EstadoPermiso()
    data object DenegadoPermanentemente : EstadoPermiso()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGestionPermisos() {

    val context = LocalContext.current
    val activity = context as? Activity

    // Comprobamos si el permiso ya está concedido
    var estadoPermiso by remember {
        val concedido = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        mutableStateOf(
            if (concedido) {
                EstadoPermiso.Concedido
            } else {
                EstadoPermiso.Pendiente
            }
        )
    }

    // Recordamos si ya hemos solicitado el permiso
    var permisoSolicitado by rememberSaveable {
        mutableStateOf(false)
    }

    // Lanzador para solicitar el permiso de cámara
    val solicitarPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->

        estadoPermiso = if (concedido) {
            EstadoPermiso.Concedido
        } else {
            val puedeMostrarExplicacion =
                activity?.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it,
                        Manifest.permission.CAMERA
                    )
                } ?: false

            if (permisoSolicitado && !puedeMostrarExplicacion) {
                EstadoPermiso.DenegadoPermanentemente
            } else {
                EstadoPermiso.Denegado
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Permisos de AppDummy")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Interfaz diferente para cada estado
            when (estadoPermiso) {

                // ESTADO 1: permiso concedido
                EstadoPermiso.Concedido -> {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Permiso concedido",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Puedes usar la cámara en AppDummy.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // ESTADO 2: todavía no se ha concedido el permiso
                EstadoPermiso.Pendiente -> {

                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Permiso de cámara pendiente",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Necesitamos tu permiso para acceder a la cámara.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            permisoSolicitado = true
                            solicitarPermiso.launch(
                                Manifest.permission.CAMERA
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Conceder permiso de cámara")
                    }
                }

                // ESTADO 3: permiso denegado, pero se puede volver a solicitar
                EstadoPermiso.Denegado -> {

                    Icon(
                        imageVector = Icons.Default.Block,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Permiso denegado",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Has rechazado el acceso a la cámara. " +
                                "Puedes volver a intentarlo.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            permisoSolicitado = true
                            solicitarPermiso.launch(
                                Manifest.permission.CAMERA
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Volver a solicitar permiso")
                    }
                }

                // ESTADO 4: permiso denegado permanentemente
                EstadoPermiso.DenegadoPermanentemente -> {

                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Acceso a la cámara bloqueado",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Para permitir el acceso, abre los ajustes " +
                                "de la aplicación y modifica el permiso.",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedButton(
                        onClick = {
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            ).apply {
                                data = Uri.fromParts(
                                    "package",
                                    context.packageName,
                                    null
                                )
                            }

                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Abrir ajustes de la aplicación")
                    }
                }
            }
        }
    }
}