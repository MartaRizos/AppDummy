# AppDummy

Práctica guiada de desarrollo de aplicaciones Android con Kotlin y Jetpack Compose.

## Corrección de versiones en libs.versions.toml

Para evitar errores de compilación, se recomienda revisar las versiones de las dependencias en el archivo `libs.versions.toml`.

Para hacer compatible el proyecto con la versión **compileSdk 36** (`build.gradle.kts(:app)`), debe cambiarse las versiones para las librerías `androidx.core:core-ktx` y `androidx.lifecycle:lifecycle-runtime-compose-android` a versiones compatibles, `coreKtx = "1.18.0"` y `lifecycleRuntimeKtx = "2.10.0"`.

La sección `[versions]` del archivo `libs.versions.toml` debe quedar de la siguiente manera:


[versions]
agp = "9.2.1"
coreKtx = "1.18.0"
junit = "4.13.2"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
lifecycleRuntimeKtx = "2.10.0"
activityCompose = "1.13.0"
kotlin = "2.4.0"
composeBom = "2026.06.01"

### Decisiones propias

- Scroll en PantallaBienvenida.kt para que pueda verse todo el contenido con la orientación 
  horizontal.
- En PantallaListado.kt, he cambiado el Modifier de la función ItemLibro ya que en una rejilla de dos columnas una
  anchura de 200 podría ser excesiva.
- En PantallaListado.kt, he renombrado la variable coincideGenero por coincideAutor, ya que se usa para la búsqueda
  de autor.
- Insertado icono nocover.png en la carpeta drawable.

#### Permisos declarados
- Permiso normal de acceso a internet: <uses-permission android:name="android.permission.INTERNET" />
  se concede al instalar la app y no hay que pedirlo. CAMERA, en cambio, es un permiso peligroso y hay que solicitarlo
  en tiempo de ejecución.