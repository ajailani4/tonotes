# ToNotes

ToNotes is an Android app to manage notes. With this app we can write our notes, edit, and delete them.

## Architecture

This project is built with multi-module architecture and implements architecture that Google recommends which has three main layers, UI, Domain (optional), and Data layer.

**Reference**: [Guide to app architecture](https://developer.android.com/jetpack/guide)

## Modules
- **app**: Connects everything (a whole modules and libraries) in presentation layer.
- **core**: Collection of reusable files, resources, and utils. So, they can be used on every modules
- **note**: Directory of note feature. This directory has 3 modules related to the feature, such as note_data, note_domain, and note_ui.
    - **note_data**: Module of data layer for note feature
    - **note_domain**: Module of domain layer for note feature
    - **note_ui**: Module of UI layer for note feature
- **buildSrc**: Defines the dependencies version and dependencies itself for Gradle.

## Tech Stack and Libraries

- Kotlin
- Jetpack Compose
- Dagger Hilt
- Flow
- Coroutines
- Mockito

## Installation and Usage
Download the zip from this repository or use git clone on your terminal.

```bash
https://github.com/ajailani4/tonotes.git
```
Then, run it on your Android emulator or physical device.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
