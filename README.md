# WeatherBold ☀️

[![CI](https://github.com/Crhystian27/WeatherBold/workflows/CI/badge.svg)](https://github.com/Crhystian27/WeatherBold/actions)
[![codecov](https://codecov.io/gh/Crhystian27/WeatherBold/branch/master/graph/badge.svg)](https://codecov.io/gh/Crhystian27/WeatherBold)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org)

Aplicación Android moderna de clima con arquitectura limpia, búsqueda en tiempo real y diseño responsive.

---

## 🎯 ¿Qué es WeatherBold?

WeatherBold es una aplicación de clima que permite buscar ubicaciones en tiempo real y consultar el pronóstico del tiempo de forma rápida y visual. Diseñada con las mejores prácticas de desarrollo Android.

### Características Principales

- 🔍 **Búsqueda inteligente** de ubicaciones con debounce automático
- 🌡️ **Pronóstico detallado** de 3 días con información completa
- 📱 **Diseño responsive** adaptado a portrait y landscape
- 🎨 **Material Design 3** con soporte para modo oscuro
- ⚡ **Rendimiento optimizado** con Coroutines y Flow
- 🧪 **Alta cobertura de tests** (>80%)

---

## 🏗️ Arquitectura

La aplicación sigue **Clean Architecture** con separación clara de responsabilidades:

```
📦 presentation/  → UI (Fragments, ViewModels, Adapters)
📦 domain/        → Lógica de negocio (UseCases, Models)
📦 data/          → Datos (Repository, API, Mappers)
📦 core/          → Utilidades compartidas
```

**Principios aplicados:**
- ✅ SOLID
- ✅ Dependency Inversion
- ✅ Single Responsibility
- ✅ Separation of Concerns

---

## 🚀 Stack Tecnológico

| Categoría | Tecnología |
|-----------|-----------|
| **Lenguaje** | Kotlin |
| **Async** | Coroutines + Flow |
| **DI** | Hilt |
| **Networking** | Retrofit + OkHttp |
| **UI** | Material Design 3 + ViewBinding |
| **Navigation** | Navigation Component + Safe Args |
| **Images** | Coil |
| **Testing** | JUnit, MockK, Turbine, Truth |

---

## 📱 Pantallas

### 🔍 Búsqueda
- Lista de ubicaciones con banderas de países
- Estados: Loading, Success, Error, Empty
- Layout adaptativo (1 columna portrait / 2 columnas landscape)

### 🌤️ Detalle del Clima
- Temperatura actual, sensación térmica
- Viento, humedad, visibilidad, presión
- Pronóstico de 3 días (Hoy, Mañana, Pasado mañana)
- Layout horizontal optimizado para landscape

---

## 📸 Capturas de Pantalla

> _Próximamente: Capturas de la aplicación en funcionamiento_

---

## 🧪 Testing & Cobertura

```bash
# Ejecutar tests
./gradlew testDebugUnitTest

# Generar reporte de cobertura
./gradlew jacocoTestReport
```

**Cobertura actual:** >80%

**Tests implementados:**
- ✅ ViewModels (Search, WeatherDetail)
- ✅ UseCases (Search, GetWeatherDetail)
- ✅ Repository (WeatherRepositoryImpl)
- ✅ Mappers (WeatherMapper)
- ✅ Models (WeatherDetail, CurrentCondition)
- ✅ Utils (CountryFlagUtil, NetworkResult)

---

## ⚙️ Configuración

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Crhystian27/WeatherBold.git
   cd WeatherBold
   ```

2. **Agregar API Key**
   
   Crear archivo `local.properties` en la raíz:
   ```properties
   WEATHER_API_KEY=tu_api_key_aqui
   ```
   
   > Obtén tu API Key gratis en [WeatherAPI.com](https://www.weatherapi.com/)

3. **Compilar y ejecutar**
   ```bash
   ./gradlew assembleDebug
   ```

---

## 🔄 CI/CD

GitHub Actions configurado para ejecutarse automáticamente en cada push a `master`:

- ✅ Build automático
- ✅ Tests unitarios
- ✅ Reporte de cobertura (Codecov)
- ✅ Validación de código

---

## 📊 Métricas del Proyecto

- **Min SDK:** 21 (Android 5.0+)
- **Target SDK:** 36
- **Cobertura de tests:** >80%
- **Archivos Kotlin:** 37
- **Tiempo de búsqueda:** <600ms

---

## 👨‍💻 Autor

**Cristian David Soto Ramirez**

[![GitHub](https://img.shields.io/badge/GitHub-Crhystian27-181717?style=flat&logo=github)](https://github.com/Crhystian27)

---

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

---

⭐ **Si te gusta el proyecto, dale una estrella en GitHub!**
