# AppDummy v2 — G-APPDUMMY-V2

Nombre y apellidos: Marta Ortiz González
NIA: 13364373

## Qué hace

AppDummy V2 es una aplicación Android para consultar un catálogo de libros. Permite visualizar el listado, buscar por título o autor, filtrar por autor y marcar libros como favoritos o leídos. También incluye una pantalla de detalle, una sección de favoritos y la gestión de permisos de cámara. La aplicación utiliza Jetpack Compose y una arquitectura MVVM.

## Arquitectura

La aplicación está organizada por capas:

```text
UI (Compose)
    ↓
ViewModel (StateFlow / SharedFlow)
    ↓
Repository
    ↓
Datos
```

**Organización y clases principales:**

- `data/model/Libro.kt`: modelo de datos del libro.
- `data/repository/LibrosRepository.kt`: proporciona y gestiona los datos.
- `data/AppContainer.kt`: centraliza la instancia compartida del repositorio.
- `screens/listado/`: pantalla del catálogo, `LibrosViewModel` y `LibrosUiState`.
- `screens/detalle/`: pantalla de detalle y `DetalleViewModel`.
- `screens/favoritos/`: pantalla de favoritos y `FavoritosViewModel`.
- `screens/bienvenida/` y `screens/permisos/`: bienvenida y gestión de permisos.
- `navegacion/`: rutas, configuración de navegación y barra inferior.

## Grafo de navegación

```text
Inicio
  └── Listado
       ├── Detalle(id)
       ├── Favoritos
       └── Permisos
```

La navegación utiliza rutas tipadas y serializables con Navigation Compose. El identificador del libro se pasa como argumento a Detalle. La pantalla de inicio es el destino inicial; al entrar al listado se elimina Inicio de la pila. La barra inferior permite alternar entre Listado y Favoritos conservando el estado de las pestañas.

## Dependencias añadidas

| Librería | Para qué |
|---|---|
| Lifecycle ViewModel Compose | Integración de ViewModel con Compose. |
| Lifecycle Runtime Compose | Recogida del estado respetando el ciclo de vida. |
| Kotlin Coroutines Test | Pruebas de corrutinas y ViewModels. |
| Navigation Compose | Navegación entre pantallas. |
| Kotlin Serialization | Definición y serialización de rutas tipadas. |
| kotlinx-serialization-json | Serialización JSON. |
| Coil 3 | Carga de imágenes de portadas. |
| Material Icons Extended | Iconos adicionales de Material. |

## Decisiones propias

1. **Repositorio compartido mediante AppContainer:** permite que las distintas pantallas trabajen con los mismos datos.
2. **StateFlow y SharedFlow:** separan el estado observable de la interfaz de los eventos puntuales, como los mensajes Snackbar.
3. **Navegación tipada:** permite definir destinos y argumentos de forma estructurada, evitando rutas construidas manualmente como cadenas.

## Limitaciones conocidas

Los libros se proporcionan mediante datos estáticos y se mantienen en memoria, por lo que los cambios no persisten al cerrar la aplicación. Como mejora futura, se podría incorporar Room para almacenar los datos localmente y ampliar las pruebas unitarias para cubrir búsqueda, filtros y eventos.