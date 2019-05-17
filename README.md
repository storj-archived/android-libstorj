# android-libstorj

**Notice: This branch (v3) contains a mostly backward-compatible implementation of the android-libstorj library to work with Storj V3. The goal of this implementation is to provide a migration step towards Storj V3 for Android apps built for Storj V2. This implementation won't be maintained and supported for the long term. Once we have the official Android library for Storj V3 ready, it will be the replacement for this implementation. Please see [storj/storj](https://github.com/storj/storj) for ongoing v3 development.**

The backward-incompatible changes compared to the master branch of android-libstorj include:
- Account registration is not suppored anymore. App developers should use the Web API to Storj V3 satellites to register new user accounts and obtain API keys.
- The [Keys](android-libstorj/src/main/java/io/storj/libstorj/Keys.java) class changed from using user, password and mnemonic to using API key and encryption key. This is required to reflect the changed key management in Storj V3.
- Error handling is not as fine-grained as in the previous versions that support Storj V2. App developers should not rely on receiving an adequate error code. In most cases the error code will be either [GENERIC_ERROR](android-libstorj/blob/eeeb26c96c4701912d2c3ef08cd1a844cd042a65/android-libstorj/src/main/java/io/storj/libstorj/Storj.java#L66)  or [TRANSFER_CANCELED](android-libstorj/blob/eeeb26c96c4701912d2c3ef08cd1a844cd042a65/android-libstorj/src/main/java/io/storj/libstorj/Storj.java#L71).

## Requirements

* Android 5.0 Lollipop or newer

## Setup

Add the Gradle dependency to the `build.gradle` file of the app module:

```Gradle
dependencies {
    implementation 'io.storj:libstorj-android-v3:0.9.1'
}
```

## Usage

Use the [StorjAndroid](android-libstorj/src/main/java/io/storj/libstorj/android/StorjAndroid.java) factory to get an instance of the [Storj](android-libstorj/src/main/java/io/storj/libstorj/Storj.java) class, properly initialized for Android:

```java
Storj storj = StorjAndroid.getInstance(getContext());
```

Use the public methods of the [Storj](android-libstorj/src/main/java/io/storj/libstorj/Storj.java) class to work with the Storj network.

## Sample app

[Hello Storj](https://github.com/kaloyan-raev/hello-storj) is a sample Android app that demonstrates the setup and usage of this library.

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this program. If not, see http://www.gnu.org/licenses/.
