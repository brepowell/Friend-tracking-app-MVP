# :label: Project Title: MeetMe
Authors: Prarin Behdarvandian (aka Melody), Jardi Martinez Jordan, and Breanna Powell

Description: Consent-based tracking app where two or more people can invite others to a live tracking session for a certain duration. Use cases include concerts, conferences, conventions, fairs, or other hourly or daily events. This app enables users to locate each other for that duration. Once the time is up, the tracking session ends, maintaining privacy to all users.

## :movie_camera: Demo

To be added soon!

## :rocket: Features
* **Tracking Invite:** Send a deep link intent between two devices to invite USER #2 to join a tracking session
* **Map:** Display a Google Map of the locations of the users who are in the same tracking session

<details id=1>
<summary><h3> :bricks: Architecture Overview </h3></summary>
  
1. 📩 Invitation & Deep Link Flow
* User #1 → User #2 Invitation
  * User #1 initiates a tracking request.
  * The app generates a unique session token (e.g., UUID) and embeds it into a deep link URL.
  * The app sends this URL to User #2 via SMS using the system SMS app.

* Deep Link Handling
  * When User #2 taps the link:
    * Android launches the app via an intent filter registered for the deep link.
    * The app extracts the session token from the URI.
    * The UI presents a consent screen asking User #2 to join the tracking session.

2. 🔐 Session Management
* Consent & Session Start
  * If User #2 accepts:
    * The app notifies the backend (or peer‑to‑peer layer) that both users are ready.
    * A tracking session is created with:
      * A fixed duration
      * A list of participants
      * A shared session ID

* Real‑Time Sync
  * To be added soon!
 
3. 📍 Location Services
* Fine Location Access
  * The app requests fine location permission (ACCESS_FINE_LOCATION).
  * Manages permission state
  * Subscribes to location updates
  * Pushes location updates to the session backend

* Location Update Strategy
  * To be added soon!

4. 🗺️ Map Rendering
* Google Maps Integration
  * The UI uses Google Maps to render:
    * The live map
    * Markers for each participant
    * Camera position updates
* Map UI Flow
  * Each user’s location is observed and collected in Compose.
  * The map displays:
    * A marker for User #1
    * A marker for User #2

</details>

<details id=2>
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


<details id=3>
<summary><h3>:package: Installation / Build Instructions</h3></summary>

To be added soon!

</details>


<details id=4>
<summary><h3>:wrench: Configuration</h3></summary>

To be added soon!

</details>

<details id=5>
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
