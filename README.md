# android-libstorj

Android library for encrypted file transfer on the Storj network via bindings to [libstorj](https://github.com/Storj/libstorj).

The library includes:

* [Java API](https://github.com/Storj/java-libstorj) for working with the Storj network
* Pre-build native libraries: libstorj and all its dependencies (libuv, json-c, curl, openssl, etc.)

## Requirements

* Android 5.0 Lollipop or newer
* armeabi-v7a compatible device (most phones and tablets)

## Setup

Add the Gradle dependency to the `build.gradle` file of the app module:

```Gradle
dependencies {
    compile 'io.storj:libstorj-android:0.3'
}
```

## Usage

Use the [StorjAndroid](android-libstorj/src/main/java/io/storj/libstorj/android/StorjAndroid.java) factory to get an instance of the [Storj](https://github.com/Storj/java-libstorj/blob/master/src/main/java/io/storj/libstorj/Storj.java) class, properly initialized for Android:

```java
Storj storj = StorjAndroid.getInstance(getContext());
```

Use the public methods of the [Storj](https://github.com/Storj/java-libstorj/blob/master/src/main/java/io/storj/libstorj/Storj.java) class to work with the Storj network.

## Sample app

[Hello Storj](https://github.com/kaloyan-raev/hello-storj) is a sample Android app that demonstrates the setup and usage of this library.

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this program. If not, see http://www.gnu.org/licenses/.
