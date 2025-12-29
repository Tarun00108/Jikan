# Jikan Anime Discovery App 📱

A modern Android application built with Kotlin and Jetpack Compose that fetches anime data from the Jikan API (MyAnimeList). The app features an "Offline-First" architecture, ensuring users can view cached content even without an internet connection.

## 🛠 Tech Stack & Architecture

*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material3)
*   **Architecture:** MVVM + Repository Pattern
*   **Networking:** Retrofit + Gson
*   **Dependency Injection:** Dagger Hilt
*   **Local Database:** Room (SQLite)
*   **Image Loading:** Glide (Compose Integration)
*   **Async:** Coroutines & Flow

## ✅ Features Implemented

1.  **Home Screen:** Displays a list of Top Anime fetched from the Jikan API.
2.  **Detail Screen:** Shows comprehensive details including Synopsis, Episodes, Age Rating, and Genres.
3.  **Video Trailers:** Embedded YouTube trailer playback using WebView.
4.  **Offline Support:** Uses Room Database as the Single Source of Truth. Data is cached locally and synced when online.
5.  **Error Handling:** graceful handling of network errors with visual feedback and cached data fallback.
6.  **Constraint Handling:** Implemented a UI toggle to handle scenarios where profile/poster images are legally restricted (Text-based fallback).

## 🧐 Assumptions Made

*   **API Stability:** It is assumed the Jikan V4 API is stable and the rate limits are not exceeded during normal testing.
*   **Trailer Availability:** Not all anime have trailers. The app checks for a valid URL; if null, it falls back to the poster image.
*   **Data Consistency:** The app assumes the "Top Anime" list order generally remains consistent between the API fetch and the local database cache.
*   **Genre Storage:** For simplicity in the local database, the list of Genres is stored as a concatenated String rather than a separate relational table.

## ⚠️ Known Limitations

1.  **Video Player:** The trailer uses a simple `WebView` to load the YouTube URL. A production app might use the official YouTube Player SDK for better controls.
2.  **Pagination:** The current implementation fetches the first page of "Top Anime". Infinite scrolling (Pagination) is not currently implemented.
3.  **Cast List:** The main Jikan "Details" endpoint does not provide the full cast list (it requires a separate API call). Therefore, only Genres and Studio details are shown.

## 🚀 How to Run

1.  Clone the repository.
2.  Open in Android Studio (Koala or newer recommended).
3.  Sync Gradle dependencies.
4.  Run on an Emulator or Physical Device (Internet required for first launch).