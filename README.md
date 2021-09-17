# BookMultiplatform
API
https://developer.nytimes.com/

The project is broken up into three different directories:
- shared
- app
- ios

Finally the shared directory holds the shared code. The shared directory is actually an android library that is referenced from the app project. This library contains directories for the different platforms as well as directories for testing.
- androidMain
- iosMain
- commonMain
- androidTest
- iosTest
- commonTest

# Overall Architecture
- MVVM with clean architecture
- What's shared?
  - Models
  - Networking: kotlinx.serialization + ktor
  - Data Storage: SQLDelight + multiplatform-settings
  - ViewModels
  - Date time: DateTime
  - Log: Kermit
  - Injection: Koin
