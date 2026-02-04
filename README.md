# 🏋️ Fitify – Android Fitness App

A modern Android application for browsing, searching, and playing fitness exercises. This project serves as a showcase of **Clean Architecture** implementation, the **MVVM** pattern, and advanced asynchronous operations in **Kotlin**.

## 📱 App Preview

*(Place screenshots or GIFs here in the future)*

## 🛠 Tech Stack

The project is built on the modern tech stack recommended by Google (Modern Android Development):

* **Language:** [Kotlin](https://kotlinlang.org/) (100%)
* **UI:** [Jetpack Compose](https://developer.android.com/jetbrains/compose) (Material Design 3)
* **Architecture:** MVVM (Model-View-ViewModel), Clean Architecture
* **Asynchronous Operations:** Kotlin Coroutines & Flow
* **Dependency Injection:** [Koin](https://insert-koin.io/)
* **Networking:** Retrofit / OkHttp
* **Serialization:** Kotlinx Serialization
* **Image Loading:** Coil
* **Multithreading:** `kotlinx.coroutines.sync.Mutex` for thread-safe caching

## 🏗 Architecture and Design

The application is divided into layers following **Separation of Concerns** principles:

### 1. Data Layer (`data`)
Handles API communication and data management.
* **Repository Pattern:** `ExerciseRepository` acts as the Single Source of Truth.
* **Thread-Safe Caching:** Custom in-memory caching implemented using `Mutex` and the double-checked locking pattern. This ensures safe data read/write even when accessed from multiple threads simultaneously (solving Race Conditions).

### 2. Domain / Business Logic
* Contains interactors (where applicable) and platform-independent domain models.
* Handles search logic and exercise filtering.

### 3. Presentation Layer (`view`, `viewmodel`)
* **MVVM:** UI logic is separated into ViewModels (`ListViewModel`, `DetailViewModel`), which survive configuration changes.
* **State Management:** Utilizing `StateFlow` for reactive UI state management.
* **UI Mappers:** Data from the repository are mapped to clean UI models (`ExerciseUiModel`, `SearchUiModel`), decoupling Android dependencies from the business logic.

## 🚀 Key Features

* **Exercise List:** Fetching data from a REST API.
* **Smart Search:** Instant filtering within the local cache without additional server requests.
* **Exercise Detail:** Displaying instructions, video, and metadata.
* **Video Player:** Integrated player for exercise demonstrations.
* **Error Handling:** Robust handling of network errors and loading states.

## 📂 Project Structure

```text
com.project.fitify
├── common          # Shared utilities, Extensions, Resource provider
├── data            # API services, DTO models, Repositories
├── di              # Koin modules (Dependency Injection)
├── view            # Jetpack Compose Screens and components
│   ├── detail      # Detail Screen
│   └── list        # Exercise List and Search
└── viewmodel       # ViewModels and UI Mappery
