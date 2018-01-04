# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# JNI bridge to libstorj

add_library( # Sets the name of the library.
             storj-java

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ../java-libstorj/src/main/cpp/storj-java.cpp )

# libstorj

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

# json-c

execute_process(COMMAND sh autogen.sh WORKING_DIRECTORY ../json-c)
execute_process(COMMAND ./configure WORKING_DIRECTORY ../json-c)

add_library( # Sets the name of the library.
             json-c

             # Sets the library as a static library.
             STATIC

             # Provides a relative path to source file(s).
             ../json-c/arraylist.c
             ../json-c/debug.c
             ../json-c/json_c_version.c
             ../json-c/json_object.c
             ../json-c/json_object_iterator.c
             ../json-c/json_pointer.c
             ../json-c/json_tokener.c
             ../json-c/json_util.c
             ../json-c/json_visit.c
             ../json-c/linkhash.c
             ../json-c/printbuf.c
             ../json-c/random_seed.c
             ../json-c/strerror_override.c )

# Directories with headers

include_directories( include/ ../libstorj/src/ ../ )

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