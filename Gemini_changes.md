To upgrade your project to a JVM version newer than 17, Gemini recommended moving to Java 21, which is the current Long-Term Support (LTS) version.
I have performed the following updates to the project:

1. Gradle Wrapper: Upgraded to Gradle 8.7 in gradle-wrapper.properties to support Java 21.
2. Android Gradle Plugin (AGP): Updated to 8.4.0 in the root build.gradle.
3. Kotlin: Updated to 1.9.23 in the root build.gradle.
4. Java Version: Set sourceCompatibility, targetCompatibility, and jvmTarget to 21 in app/build.gradle.
5. SDK Versions: Updated compileSdk and targetSdk to 34 to ensure compatibility with modern tools.
6. Compose: Updated the Compose Compiler extension to 1.5.11 (compatible with Kotlin 1.9.23) and bumped Compose UI dependencies to 1.6.4.
7. Dependencies: Updated several core libraries (Core KTX, Lifecycle, Activity, etc.) to versions compatible with the new AGP and Kotlin versions.
