# AppDummy v1 — G-APPDUMMY-V1

Nombre y apellidos: Marta Ortiz González
NIA: 13364373

## Qué hace

AppDummy es una aplicación Android desarrollada con Kotlin y Jetpack Compose para practicar la 
creación de interfaces de usuario.

Incluye una pantalla de bienvenida con validación del nombre, un listado de libros con búsqueda por 
título y filtros por autor, y una pantalla para gestionar el permiso de cámara con 4 estados.

El listado permite marcar libros como leídos o favoritos y compartir el título y el autor mediante 
el selector de aplicaciones de Android.

Las pantallas se pueden visualizar de manera independiente mediante las funciones @Preview, sin 
necesidad de implementar navegación entre ellas. Además, se pueden emular también independientemente
desde MainActivity.kt .

## Estructura

- MainActivity.kt: actividad principal desde la que se selecciona la pantalla que se ejecuta.
- PantallaBienvenida.kt: pantalla de bienvenida con campo de texto, validación del nombre y botón de 
entrada.
- screens/PantallaListado.kt: listado de libros con búsqueda, filtros por autor, tarjetas, favoritos,
estado de lectura y opción para compartir.
- screens/PantallaGestionPermisos.kt: pantalla para solicitar y gestionar el permiso de cámara y 
mostrar sus diferentes estados.
- res/drawable/nocover.png: imagen alternativa para las portadas de los libros.
- AndroidManifest.xml: declaración de la actividad principal y de los permisos de la aplicación.
- gradle/libs.versions.toml: catálogo de versiones de Gradle y de las dependencias del proyecto.

## Dependencias añadidas

Librería | Para qué
--- | ---
Jetpack Compose | Crear las interfaces mediante funciones @Composable.
Material 3 | Utilizar componentes visuales como Scaffold, TopAppBar, Card, OutlinedTextField, 
FilterChip e IconButton.
Coil 3 | Cargar las portadas de los libros desde Internet mediante AsyncImage.
AndroidX Activity Compose | Integrar Jetpack Compose con la actividad Android.
AndroidX Core KTX | Utilizar funcionalidades de Android, incluida la comprobación de permisos.
AndroidX Lifecycle | Incorporar las dependencias relacionadas con el ciclo de vida de Android 
configuradas en el proyecto.

## Permisos declarados

Permiso | Por qué es necesario
--- | ---
android.permission.INTERNET | Permite cargar las portadas de los libros desde Internet. Es un 
permiso normal que no requiere solicitar autorización al usuario en tiempo de ejecución.
android.permission.CAMERA | Permite solicitar acceso a la cámara. Es un permiso peligroso que 
requiere autorización en tiempo de ejecución en las versiones de Android correspondientes.

## Decisiones propias

1. Adaptación del tamaño de las tarjetas: he utilizado Modifier.fillMaxWidth() en ItemLibro para que 
las tarjetas se adapten al ancho disponible en la cuadrícula de dos columnas, evitando establecer 
2. una anchura fija que pudiera resultar excesiva.

2. Mejora del filtrado por autor: he renombrado la variable coincideGenero a coincideAutor, ya que 
la condición se utiliza para filtrar los libros por autor y no por género.

3. Diferenciación de los estados del permiso: en PantallaGestionPermisos.kt he utilizado una 
estructura when para mostrar interfaces diferentes para los estados Denegado y
DenegadoPermanentemente. También he añadido la variable permisoSolicitado para tener en cuenta si ya 
se ha solicitado el permiso.

## Limitaciones conocidas

- Las pantallas se prueban de manera independiente. En esta versión no se ha implementado navegación 
entre ellas.
- El catálogo utiliza una lista de libros de ejemplo definida en el código y no dispone de una base 
de datos para guardar los libros de forma permanente.
- Los cambios de estado de leído y favorito se mantienen en el estado de la pantalla, pero no se 
guardan de forma permanente.
- Las portadas se cargan desde Internet, por lo que su disponibilidad depende de la conexión y de 
las direcciones URL utilizadas.
- La imagen nocover.png se utiliza como portada alternativa cuando la URL no es válida. El 
comportamiento ante errores de descarga debe comprobarse por separado.
- La pantalla de gestión de permisos permite practicar la solicitud y la denegación del permiso de 
cámara, pero no implementa una funcionalidad para utilizar realmente la cámara.
- La identificación de una denegación permanente depende de la lógica implementada y del 
comportamiento del sistema Android.