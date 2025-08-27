# Weather Now & Later App

The **DailyForecast Task** app provides users with real-time weather updates, including daily and weekly forecasts. The app leverages modern architecture to ensure a smooth user experience with offline capabilities, using OpenWeatherMap APIs. For API documentation, visit: [OpenWeatherMap API Docs](https://openweathermap.org/forecast5).

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Testing](#testing)
- [CI/CD](#cicd)

## Features

- **Forecast**: Provides daily and weekly forecasts depending on the selected city.
- **Offline Mode**: Caches data for offline access, allowing users to view weather updates even without an internet connection.
- **User-Friendly UI**: Intuitive and responsive design built with **Jetpack Compose**.
- **Location-Based Updates**: Automatically fetches weather information based on the user's city.
- **Data Persistence**: Stores user preferences and weather data using **DataStore** and **Room**.

## Installation

Clone the repository:

```bash
git clone https://github.com/mostafa4mohamed/DailyForecast-Task.git
```

## Architecture

The project follows **Clean Architecture**, ensuring clear separation of concerns and modularity. The app is structured into distinct layers to keep the core business logic isolated from external dependencies. The core business logic is placed in the **Domain Layer**, which is independent of other layers.

The layers are as follows:
- **Domain Layer**: Contains entities and use cases, representing the core logic.
- **Data Layer**: Handles data operations, including API interactions via **Retrofit** and local storage via **Room**.
- **App Layer**: Uses **Jetpack Compose** to create the UI and **ViewModel** to manage UI-related data and handle interactions with the domain layer.

The app also follows the **MVVM (Model-View-ViewModel)** design pattern, where:
- **Model** represents the data and business logic.
- **View** represents the UI components (built using **Jetpack Compose**).
- **ViewModel** acts as a bridge, managing UI-related data and handling logic between the **Model** and **View** layers.

For the 7-day forecast list, the app follows the **MVI (Model-View-Intent)** pattern, which is well-suited for handling complex UI states such as loading, error, and success. The layers are as follows:
- **Model**: Contains the weather data and business logic. 
- **View**: The Compose UI that displays the 7-day forecast. 
- **Intent**: The user's actions, such as selecting a city. 
- **State**: The various states (loading, success, error) that the view will display.

This architecture ensures clear separation of concerns, easy testing, maintainable, and scalability.

## Technologies

- **Clean Architecture**: The app is built using **Clean Architecture**, ensuring clear separation of concerns. The core business logic is isolated in the domain layer, while other layers (data and app) interact with it through well-defined interfaces.
- **Kotlin**: The primary programming language.
- **MVVM**: Used to separate the UI, data, and business logic.
- **MVI**: Used specifically for managing the 7-day forecast list, providing a clear structure for handling UI states.
- **Room**: For offline caching and local storage.
- **Coroutines**: For handling asynchronous tasks.
- **Hilt**: For dependency injection.
- **Retrofit**: For API interactions and network requests.
- **Jetpack Compose**: A modern UI toolkit to build declarative and responsive user interfaces.
- **DataStore**: Used for managing user preferences and lightweight data storage.
- **MockK**: A powerful mocking library used for testing in Kotlin, enabling the creation of mocks for complex dependencies during unit tests.
- **JUnit4**: A widely used testing framework for unit tests in Java and Kotlin.
- **GitHub Actions**: For CI/CD, automating the build and testing process.
- **Material Design**: The app follows Material Design guidelines to ensure a consistent and user-friendly interface.
- **Kotlin Flow**: Used for handling asynchronous data streams and managing state in a reactive manner.
- **Coil**: A powerful image loading library for Android, used for loading and displaying images efficiently in the app.
- **OkHttp**: A networking library that works with Retrofit for making HTTP requests and handling responses efficiently.

## Testing

The project includes unit tests and UI tests to ensure code reliability and stability. Testing is done using JUnit4 and MockK for mocking dependencies.
Running Tests

You can run tests using the following Gradle command:
```
./gradlew test
```

## CI/CD

*The project uses GitHub Actions to automate the following tasks:*
- **Lint**: Runs a lint check to ensure code quality.
- **Unit Tests**: Executes unit tests to validate business logic.
- **Build APK**: Generates a release APK for distribution.

*GitHub Actions Configuration*

The CI/CD workflow is defined in .github/workflows/android.yml and includes the following steps:
- **Lint**: Runs the Android Lint tool to check for code quality issues.
- **Unit Tests**: Runs unit tests using Gradle.
- **Build APK**: Builds the APK for release.
