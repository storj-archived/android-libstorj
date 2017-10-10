# android-libstorj

Android library for encrypted file transfer on the Storj network via bindings to [libstorj](https://github.com/Storj/libstorj).

The library includes:

* Java API for working with the Storj network
* Pre-build native libraries: libstorj and all its dependencies (libuv, json-c, curl, openssl, etc.)

## Requirements

* Android 5.0 Lollipop or newer
* armeabi-v7a compatible device (most phones and tablets)

## Setup

### 1. Provide the Gradle dependency

Add the dependency to the `build.gradle` file of the app module:

```
dependencies {
    compile 'io.storj:libstorj-android:0.1'
}
```

### 2. Set the app directory

The app directory is where the authentication files with encryption keys are created. Usually this is done in the user's home directory. However, there is no such user's home directory on the Android platform. Instead there is a separate _data directory_ for each Android application where it can write internal data files.

It would be best if your Android app extends the [Application](https://developer.android.com/reference/android/app/Application.html) class and set the app directory in the `onCreate()` method like this:

```java
Storj.appDir = getFilesDir().getPath();
```

### 3. Set the temp directory

The sharding process in libstorj requires the usage of the temp directory. However, there is no global temp directory in the Android platform. Your Android app should set the temp directory to the app's cache directory using the `STORJ_TEMP` environment variable.

It would be best if your Android app extends the [Application](https://developer.android.com/reference/android/app/Application.html) class and set the `STORJ_TEMP` variable in the `onCreate()` method like this:

```java
try {
    Os.setenv("STORJ_TEMP", getCacheDir().getPath(), true);
} catch (ErrnoException e) {
    Log.e(App.class.getName(), e.getMessage(), e);
}
```

### 4. Pack and extract the CA Certs file

The libstorj library uses the [curl](https://curl.haxx.se/) library to communicate with the Storj Bridge API hosted at https://api.storj.io over a secured HTTPS connection. The proper establishment of the secure connection requires that the server certificate is verified. The verification is done against a set of Trusted Root Certificates stored in the so called CA Certs file.
  
Unfortunately does not provide such CA Certs file, so your Android app should pack it and extract it when started.

The CA Certs file can be obtained from the curl web site: https://curl.haxx.se/ca/cacert.pem

The file should be packed in the app's `assets/` directory.

Finally, upon startup the app should use the [AssetManager](https://developer.android.com/reference/android/content/res/AssetManager.html) to extract the CA Certs file to it's application directory and set the `STORJ_CAINFO` environment variable to its path. It would be best if your Android app extends the [Application](https://developer.android.com/reference/android/app/Application.html) class and do it like this in the `onCreate()` method:

```java
new Thread() {
    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        String filename = "cacert.pem";
        AssetManager assetManager = getAssets();
        try (InputStream in = assetManager.open(filename);
            OutputStream out = openFileOutput(filename, Context.MODE_PRIVATE)) {
            copy(in, out);
            Os.setenv("STORJ_CAINFO", getFileStreamPath(filename).getPath(), true);
        } catch (IOException | ErrnoException e) {
            Log.e(App.class.getName(), e.getMessage(), e);
        }
    }
}.start();
```

Note the usage of the background thread to avoid any potential responsiveness issues during app startup.

## Usage

The [Storj](libstorj/src/main/java/io/storj/libstorj/Storj.java) class is the main entry point to the Java API.

## Sample app

[Hello Storj](https://github.com/kaloyan-raev/hello-storj) is a sample Android app that demonstrates the setup and usage of this library.

Pay attention on the [App](https://github.com/kaloyan-raev/hello-storj/blob/master/app/src/main/java/name/raev/kaloyan/hellostorj/App.java) class implementation.

## License

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this program. If not, see http://www.gnu.org/licenses/.
