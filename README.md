# :label: Project Title: MeetMe
Authors: Prarin Behdarvandian (aka Melody), Jardi Martinez Jordan, and Breanna Powell

Description: Consent-based tracking app where two or more people can invite others to a live tracking session for a certain duration. Use cases include concerts, conferences, conventions, fairs, or other hourly or daily events. This app enables users to locate each other for that duration. Once the time is up, the tracking session ends, maintaining privacy to all users.

## :movie_camera: Demo

To be added soon!

## :rocket: Features
* **Tracking Invite:** Send a deep link intent between two devices to invite USER #2 to join a tracking session
* **Map:** Display a Google Map of the locations of the users who are in the same tracking session

## :bricks: Architecture Overview

To be added soon!


<details id=1>
<summary><h3>🛠️ Tech Stack</h3></summary>

### **Language & Platform**
- **Kotlin** — primary development language  
- **Android SDK** — modern Android APIs with backward compatibility

### **UI Framework**
- **Jetpack Compose** — declarative UI for building screens and components  
- **Material 3 (Material You)** — theming, typography, icons, and UI components

### **Navigation**
- **Navigation Compose** — in-app navigation, route arguments, and deep link support

### **State & Architecture**
- **Kotlin Coroutines** — asynchronous operations and structured concurrency  
- **StateFlow + collectAsState** — reactive UI state management  
- **DataStore (Preferences)** — persistent storage for user settings (`SettingsDataStore`, `SettingsState`)

### **Android System Integrations**
- **Intents & URI Handling** — opening external apps and links  
- **ContextCompat** — compatibility helpers for launching activities

### **Tooling & Libraries**
- **AndroidX Libraries** — modern support libraries across UI, navigation, and annotations  
- **Gradle** — Android build system (Kotlin DSL or Groovy)

</details>


<details id=2>
<summary><h3>:package: Installation / Build Instructions</h3></summary>

To be added soon!

</details>


<details id=3>
<summary><h3>:wrench: Configuration</h3></summary>

To be added soon!

</details>

<details id=4>
<summary><h3>:test_tube: Testing</h3></summary>

To be added soon!

</details>

## :file_folder: Project Structure

### Navigation:
The main files are contained within this folder: [UI Files](MeetMe/app/src/main/java/com/example/meetme/ui)
* [ScreenConsent.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenConsent.kt) - Displays request to User #2 to join a tracking session, asks for consent
* [ScreenHelp.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenHelp.kt) - Help screen with information about the app
* [ScreenMap.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenMap.kt) - Map navigation screen that displays locations while tracking is active
* [ScreenSettings.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenSettings.kt) - Users can toggle and set preferences
* [ScreenTrackingStart.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenTrackingStart.kt) - Displays that tracking has started
* [ScreenTrackingStop.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenTrackingStop.kt) - Displays that tracking has ended
* [ScreenWaitingForConsent.kt](MeetMe/app/src/main/java/com/example/meetme/ui/ScreenWaitingForConsent.kt) - Displays that a tracking invitation was sent to User #2

## :handshake: Contributing Guidelines

To be added soon!

## :scroll: License

To be added soon!

## 🙋🏼 Contact / Maintainers

To be added soon!

## :star: Acknowledgments
This started off as a Mobile Computing course assignment. Thank you to Professor Hansel Ong for your encouragement!
