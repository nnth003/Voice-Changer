<p align="center">
 <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
 <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">
Base project Android with Jetpack Compose, Hilt, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM architecture.
</p>
</br>

# Tech stack & Open-source libraries
- Minimum SDK level 24
- [Jetpack Compose](https://developer.android.com/jetpack/compose) is Android’s modern toolkit for building native UI. It simplifies and accelerates UI development on Android. Quickly bring your app to life with less code, powerful tools, and intuitive Kotlin APIs.
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Hilt](https://dagger.dev/hilt/) for dependency injection.
- Jetpack
    - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - ViewBinding - Is a feature that allows you to more easily write code that interacts with views. Once view binding is enabled in a module, it generates a binding class for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.
    - Room Persistence - Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- Architecture
    - MVVM Architecture (Model - View - ViewModel)
    - [Repository Pattern](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0)
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Moshi](https://github.com/square/moshi) - Moshi is a modern JSON library for Android, Java and Kotlin.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API.
- [Material3](https://m3.material.io/develop/android/jetpack-compose) - Material 3 is the latest version of Google’s open-source design system. Design and build beautiful, usable products with Material 3.
- [Baseline Profiles](https://developer.android.com/topic/performance/baselineprofiles/overview) - Baseline Profiles improve code execution speed by around 30% from the first launch.

# License
```xml
Designed and developed by 2023 pixelzlab

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```