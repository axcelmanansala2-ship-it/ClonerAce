# ClonerAce 🎯

A fully functional Android App Cloner (Parallel Space / Dual Space clone) built in Kotlin.

## Features

- **App Listing** – Browse all installed user apps with icons, names, package names, and sizes
- **App Cloning** – Clone any installed app into an isolated virtual space with a unique package name
- **Virtual Space** – Supports both rooted (Linux namespaces) and non-rooted (Work Profile) devices
- **Game Guardian** – Built-in compatibility for memory editing; detect, attach, or download Game Guardian
- **Root Support** – Auto-detects root availability and chooses the best cloning method
- **Dark Mode** – Full Material Design 3 with dynamic dark/light theming
- **Search & Sort** – Filter apps by name or package, sort by name/size/date
- **Progress Tracking** – Real-time progress dialog during cloning
- **WorkManager** – Background cloning and cleanup tasks

## Architecture

- **MVVM** with ViewModel + LiveData
- **Hilt** for dependency injection
- **Room** for local persistence of cloned app metadata
- **Navigation Component** with bottom navigation (3 tabs)
- **Coroutines + Flow** for async operations
- **ViewBinding** throughout

## Package Structure

```
com.appcloner/
├── AppClonerApplication.kt
├── data/           (database, models, repositories, local prefs)
├── domain/         (use cases, domain models)
├── presentation/   (activities, fragments, adapters, viewmodels, dialogs)
├── service/        (APK modifier, signer, virtual space, root executor, GG helper)
├── utils/          (package utils, file utils, logger, extensions)
├── admin/          (device admin receiver and manager)
├── worker/         (WorkManager workers for clone and cleanup)
└── di/             (Hilt module)
```

## Build

Automated via GitHub Actions on every push to `main`.

### Requirements

- Android Studio Hedgehog or newer
- JDK 17
- Gradle 8.9
- Android SDK 34

### Run locally

```bash
gradle assembleDebug
```

### CI/CD

Push to `main` → GitHub Actions builds `clonerface-debug.apk` automatically.

## Supported Android Versions

- **Minimum:** Android 8.0 (API 26)
- **Target:** Android 14 (API 34)

## Permissions

| Permission | Purpose |
|---|---|
| QUERY_ALL_PACKAGES | List installed apps |
| SYSTEM_ALERT_WINDOW | Virtual space overlay |
| MANAGE_EXTERNAL_STORAGE | APK extraction |
| REQUEST_INSTALL_PACKAGES | Install cloned APKs |
| FOREGROUND_SERVICE | Clone background service |
| POST_NOTIFICATIONS | Clone progress notifications |

## Known Limitations

- Full APK re-signing requires a valid debug keystore (auto-generated)
- Work Profile virtual space requires device admin permission
- Some banking/DRM-protected apps may not clone correctly
- Root features require a rooted device with `su` binary

## Security Notes

- Cloned app metadata is stored in Room database (encrypted at rest via Android Keystore recommended)
- Critical system apps are blocked from cloning
- APK signatures are validated before cloning
