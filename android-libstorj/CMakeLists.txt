# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
             storj-java

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ../java-libstorj/src/main/cpp/storj-java.cpp )

add_library( # Sets the name of the library.
             storj

             # Sets the library as a static library.
             STATIC

             # Provides a relative path to source file(s).
             ../libstorj/src/bip39.c
             ../libstorj/src/cli.c
             ../libstorj/src/crypto.c
             ../libstorj/src/downloader.c
             ../libstorj/src/http.c
             ../libstorj/src/rs.c
             ../libstorj/src/storj.c
             ../libstorj/src/uploader.c
             ../libstorj/src/utils.c )

include_directories( include/ ../libstorj/src/ )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

find_library( # Sets the name of the path variable.
              zlib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              z )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

add_library(json-c SHARED IMPORTED)
set_target_properties(json-c
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libjson-c.so )

add_library(crypto STATIC IMPORTED)
set_target_properties(crypto
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libcrypto.a)

add_library(ssl STATIC IMPORTED)
set_target_properties(ssl
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libssl.a)

add_library(curl STATIC IMPORTED)
set_target_properties(curl
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libcurl.a)

add_library(uv STATIC IMPORTED)
set_target_properties(uv
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libuv.a)

add_library(nettle STATIC IMPORTED)
set_target_properties(nettle
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libnettle.a)

add_library(microhttpd SHARED IMPORTED)
set_target_properties(microhttpd
  PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/libs/${ANDROID_ABI}/libmicrohttpd.so )

target_link_libraries( storj-java storj json-c curl ssl crypto ${zlib} uv nettle microhttpd ${log-lib} )