# SimpleVSRG

Un juego de ritmo VSRG (Vertical Scrolling Rhythm Game) simple de 4 teclas para Android, similar a osu!mania.

## 🎮 Características

- **4 teclas/lanes** (D, F, J, K)
- **Nivel de prueba de 30 segundos**
- **Sistema de puntuación** (Perfect: 100pts, Good: 50pts)
- **Sistema de combos**
- **Patrones de ritmo simples** a 120 BPM

## 📦 Cómo abrir el proyecto en Android Studio

### Requisitos
- **Android Studio** (versión Hedgehog 2023.1.1 o superior)
- **JDK 17** o superior
- **Android SDK** con API Level 34

### Pasos para importar

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/MiguelV467/SimpleVSRG.git
   cd SimpleVSRG
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona `File > Open`
   - Navega a la carpeta `SimpleVSRG` y selecciónala
   - Espera a que Gradle sincronice el proyecto

3. **Configurar SDK (si es necesario)**
   - Ve a `File > Project Structure`
   - En `SDK Location`, asegúrate de tener configurado el Android SDK
   - En `Project`, verifica que el Gradle JDK sea versión 17

4. **Sincronizar Gradle**
   - Click en `File > Sync Project with Gradle Files`
   - Espera a que descargue las dependencias

## 🚀 Cómo compilar y ejecutar

### En un dispositivo físico

1. **Habilitar modo desarrollador** en tu dispositivo Android:
   - Ve a `Ajustes > Acerca del teléfono`
   - Toca 7 veces en `Número de compilación`
   - Regresa y entra a `Opciones de desarrollador`
   - Activa `Depuración USB`

2. **Conectar dispositivo**
   - Conecta tu teléfono por USB
   - Acepta la autorización de depuración

3. **Ejecutar app**
   - En Android Studio, selecciona tu dispositivo en el menú desplegable
   - Click en el botón verde de Play (Run) o presiona `Shift + F10`

### En un emulador

1. **Crear emulador**
   - Click en `Device Manager` (icono de teléfono)
   - Click en `Create Device`
   - Selecciona un dispositivo (recomendado: Pixel 6)
   - Descarga una imagen del sistema (API 34 - Android 14)
   - Finaliza la configuración

2. **Ejecutar**
   - Selecciona el emulador en el menú desplegable
   - Click en Run

## 🎵 Cómo jugar

1. El juego inicia automáticamente al abrir la app
2. Las notas caerán desde arriba hacia la línea de golpe
3. Presiona los botones D, F, J, K cuando las notas lleguen a la línea
4. Intenta mantener tu combo para más puntos
5. El nivel dura 30 segundos
6. Al terminar, verás tu puntuación final

## 🛠️ Estructura del proyecto

```
SimpleVSRG/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vsrg/simple/
│   │   │   │   ├── MainActivity.kt      # Actividad principal del juego
│   │   │   │   ├── GameView.kt          # Vista custom para dibujar notas
│   │   │   │   └── Note.kt              # Clase de datos para las notas
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml # Layout del juego
│   │   │   │   ├── values/
│   │   │   │   │   ├── colors.xml
│   │   │   │   │   ├── strings.xml
│   │   │   │   │   └── themes.xml
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 🔧 Personalización
### Cambiar velocidad de las notas
En `GameView.kt`, modifica:
```kotlin
private val noteSpeed = 1000f // Aumenta para más rápido
```

### Cambiar BPM del nivel
En `MainActivity.kt`, en la función `generateTestLevel()`:
```kotlin
val bpm = 120 // Cambia a tu BPM deseado
```

### Cambiar colores
Edita `app/src/main/res/values/colors.xml`

### Crear nuevos patrones
Modifica el loop en `generateTestLevel()` en `MainActivity.kt`

## 🐛 Solución de problemas

### Error: "SDK not found"
- Instala el Android SDK desde SDK Manager
- Configura la ruta en `File > Project Structure > SDK Location`

### Error al sincronizar Gradle
- Verifica tu conexión a internet
- Intenta `File > Invalidate Caches / Restart`

### La app no se instala en el dispositivo
- Verifica que la depuración USB esté habilitada
- Revisa que el dispositivo esté autorizado
- Prueba con otro cable USB

## 📝 Próximas mejoras

- [ ] Soporte para archivos .osu
- [ ] Más patrones de niveles
- [ ] Sistema de configuración (offset, velocidad)
- [ ] Efectos de sonido
- [ ] Modo 7 teclas
- [ ] Skins personalizables

## 📝 Licencia

Este proyecto es de código abierto para fines educativos.

## ✨ Autor

Creado como proyecto de prueba VSRG simple.
