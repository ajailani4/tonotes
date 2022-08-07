# ToNotes

ToNotes is an Android app for managing notes. With this app you can write your notes, edit, and delete them. Also, you can back up the notes to the cloud and sync them to your phone.

## Architecture

This app is a multi-module project that implements Clean Architecture which has three main layers, UI, Domain, and Data layer.

**References**:
- [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Clean Architecture Guide (with tested examples): Data Flow != Dependency Rule](https://medium.com/proandroiddev/clean-architecture-data-flow-dependency-rule-615ffdd79e29)

## Modules
Modularization system that this app used is hybrid or mix. It means we separated each features as a module, then we create sub modules of the layers (data, domain, and UI) for each feature modules.
- **app**: Connects everything (a whole modules and libraries) in presentation layer.
- **buildSrc**: Defines the dependencies version and dependencies itself for Gradle.
- **core**: Collection of reusable files, such as utils that can be used on every modules.
- **core_ui**: Same as core module, core_ui is more specific on reusable files related on UI, such as Composable, image resources, strings, etc.
- **account**: A module of account feature, such as login and register feature. This module has 3 sub modules related to the feature, **account_data**, **account_domain**, and **account_ui**.
- **note**: A module of note feature. Same as account module, this module also has 3 sub modules, **note_data**, **note_domain**, and **note_ui**.

## Tech Stack

- Kotlin
- Jetpack Compose
- Material Design 3
- Dagger Hilt
- Flow
- Coroutines
- Room
- Retrofit
- Moshi
- WorkManager
- Mockito

## Screenshots
**Home Screen**<br/>
![1](https://i.postimg.cc/nhYBr02g/Screenshot-2022-08-07-20-38-49-791-com-tonotes-app.jpg)

**Backup Types**<br/>
![2](https://i.postimg.cc/85tStJGt/Screenshot-2022-08-07-20-39-01-184-com-tonotes-app.jpg)

**Note Detail Screen**<br/>
![3](https://i.postimg.cc/7P5Hm9Cz/Screenshot-2022-08-07-20-39-37-950-com-tonotes-app.jpg)

**Add and Edit a Note**<br/>
![4](https://i.postimg.cc/pd0vB5Rk/Screenshot-2022-08-07-21-05-35-610-com-tonotes-app.jpg)

**Search Notes**<br/>
![5](https://i.postimg.cc/HxRCqGJy/Screenshot-2022-08-07-21-06-25-560-com-tonotes-app.jpg)

## Installation and Usage
Download the zip from this repository or use git clone on your terminal.

```bash
https://github.com/ajailani4/tonotes.git
```
Then, run it on your Android emulator or physical device.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
