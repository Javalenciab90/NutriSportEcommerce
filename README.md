
# NutriSport Ecommerce

Es una aplicación Kotlin Multiplatform (KMP) construida con Compose Multiplatform, diseñada para compartir la mayor parte del código entre Android, iOS y Desktop.
El proyecto sigue una arquitectura modular para permitir escalabilidad, mantenibilidad y la posibilidad de integrar código nativo por plataforma sin afectar al código común.

## 📹 Demo Video

[![Watch the video](https://github.com/user-attachments/assets/f19fdcd8-e7cd-445a-b420-963e557b5f1f)](
https://youtu.be/GpuKyet1vcE)


## 🧱 Arquitectura del Proyecto
El proyecto está organizado en módulos independientes que se comunican mediante dependencias bien definidas.
Cada módulo contiene tres fuentes principales:
commonMain → Lógica compartida (Kotlin Multiplatform)
androidMain → Implementaciones específicas de Android
iosMain → Implementaciones específicas de iOS
Esto permite que cualquier funcionalidad que necesite código nativo pueda desarrollarse sin afectar a la otra plataforma.

## Módulos

- 📦 Proyecto NutriSport Ecommerce  
  - 🎨 composeApp  
  - 🗄️ data  
  - 🧩 di  
  - 🌟 feature  
    - 👤 Profile  
    - 🛠️ admin_panel  
    - 🔐 auth  
    - 🛍️ details  
    - 🏠 home  
      - 🛒 cart  
        - 💳 checkout  
      - 📂 categories  
        - 🔎 category_search  
      - 🛍️ products_overview  
  - 🧭 navigation  
  - ♻️ shared  

## 📚 Resumen y descripción de las librerías utilizadas
A continuación se presenta una explicación de cada librería incluida en el proyecto NutriSport Ecommerce, detallando su propósito.

### 🎨 UI & Compose Multiplatform
- Compose Multiplatform — compose-multiplatform
    Permite escribir interfaces gráficas declarativas usando Compose y compartirlas entre Android, iOS y Desktop.
- androidx.activity-compose
    Permite integrar actividades de Android con Compose, manejando el ciclo de vida de manera optimizada.
- androidx.lifecycle
    Incluye ViewModel y lifecycle-runtime-compose, manejando estados y ciclos de vida tanto en Android como en el código KMP.
- androidx.ui-tooling
    Incluye herramientas de inspección visual como Preview, útiles durante el desarrollo en Android Studio.
- splash-screen
    Muestra una pantalla de carga nativa al iniciar la aplicación en Android, antes de que Compose se renderice.

### 🧭 Navegación
- compose-navigation — navigation
    Sistema de navegación declarativa para Compose Multiplatform.
    Permite estructurar rutas, pantallas y flujos tanto en Android como en iOS desde un único código compartido.

### 🔐 Autenticación & Notificaciones (KMP)
- auth-kmp / auth-firebase-kmp — auth
    Librería multiplataforma para facilitar autenticación con Google y Firebase desde código KMP.
    Evita escribir implementaciones nativas por plataforma.
- kmp-notifier — notifier
    Permite mostrar notificaciones locales de forma multiplataforma.
    Soporta Android, iOS y Desktop con una sola API.

### 🔥 Firebase (Multiplataforma)
Librerías de Firebase para KMP desarrolladas por GitLive:
- firebase-app
    Inicializa Firebase en entornos multiplataforma.
- firebase-firestore
    Permite leer/escribir documentos de Firestore desde KMP.
- firebase-storage
    Manejo de archivos (imágenes, PDFs, etc.) dentro de Firebase Storage.
- firebase-common
    Componentes compartidos para todas las integraciones de Firebase.
    Estas librerías evitan escribir código nativo en Swift/Kotlin al interactuar con Firebase.

### 🧩 Inyección de Dependencias
- Koin — koin-core, koin-compose, koin-android, koin-compose-viewmodel
    Framework sencillo de DI compatible con KMP.
    Usado para:
    Registrar repositorios
    Proveer ViewModels
    Compartir instancias entre módulos
    Integrar DI con Compose

### 🔄 Serialización & Modelo de Datos
- kotlinx-serialization-json — serialization
    Serialización JSON multiplataforma usada para:
    Requests y responses del API (Ktor)
    Persistencia local (settings)
    Transferencia de datos entre capas

### 🌐 Red / API Client
- Ktor (Client) — ktor
    Cliente HTTP multiplataforma utilizado para consumir APIs REST desde commonMain.
    Incluye:
    ktor-client-core (núcleo del cliente)
    ktor-client-content-negotiation (manejo de JSON)
    ktor-client-serialization (serialización con kotlinx)
    ktor-client-android (implementación Android)
    ktor-client-darwin (implementación iOS)
  
### 🌀 Concurrencia
- kotlinx-coroutines — coroutines
    Base del manejo asíncrono en KMP.
    Usado en:
    Llamadas a API
    Procesos intensivos
    Flujos de datos
    ViewModels multiplataforma

### 🖼️ Carga de Imágenes
- Coil 3 (KMP) — coil3
    Librería moderna de carga de imágenes compatible con Compose Multiplatform.
    Componentes incluidos:
    coil3 (núcleo)
    coil3-compose (integración con Compose)
    coil3-compose-core (widgets básicos)
    coil3-network-ktor (descarga vía Ktor)

### 🎛️ Estado & Configuración Local
- Multiplatform Settings — settings
    Permite almacenar configuraciones de forma segura y multiplataforma.
    Usado para:
    Preferencias de usuario
    Tokens
    Configuraciones persistentes
    Incluye variantes:
    no-arg
    make-observable

### 💬 UI Utilitaria
- messagebar-kmp — messagebar
    Componente KMP para mostrar mensajes tipo barra (snackbar) customizables, similar a “toast” pero más moderno y multiplataforma.
- browser-kmp — browser
    Abre URLs externas desde la app sin tener que escribir código nativo para cada plataforma.
  
### 🔧 Plugins Importantes
- AGP — agp
    Android Gradle Plugin para compilar módulos Android.
- kotlinMultiplatform — kotlin
    Habilita el soporte real de KMP en Gradle.
- composeCompiler
    Activa el compilador que habilita funciones @Composable.
- kotlinx-serialization plugin
    Requerido para generar serializers en tiempo de compilación.
- google-services
    Necesario para inicializar Firebase en Android.
