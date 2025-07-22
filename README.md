# AndroidAssignment

A native Android app built using Kotlin, implementing a hierarchical content viewer with full offline support and robust MVI patterns.

## Features

- Fetches and displays hierarchical content pages, sections, questions from a remote API
- Font sizes visually reflect content hierarchy
- Supports both text and image questions - image opens in full screen on click
- Persists all content and structure offline using Room, for seamless offline use
- Gracefully handles network failures by serving cached data
- Clean MVI architecture, Dependency Injection with Hilt

## Setup Instructions

1. **Clone the repository:**
   ```bash
   git clone https://github.com/giorgishubitidze357/AndroidAssignment.git
   ```

2. **Open in Android Studio:**
   Open the cloned folder as a project.

3. **Build & Run:**
   Click "Run" or `Shift + F10`

4. **Dependencies:**
   All dependencies are managed via Gradle - no manual installation required.

## Requirements

- Minimum SDK: 24+
- Internet connection for initial data fetch

## Architecture

**MVI (Model-View-Intent):**
- App state is held and emitted via immutable StateFlows in the ViewModel
- User actions from the View are modeled as "Intents" and centrally processed in the ViewModel for clarity and maintainability

**Room DB:** For caching and offline recovery

**Retrofit & Moshi:** For robust, testable networking and parsing

**Jetpack Compose:** For modern, reactive UI

**Coil** For image loading
