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

# libuv

add_library( # Sets the name of the library.
             uv

             # Sets the library as a static library.
             STATIC

             # Provides a relative path to source file(s).
             ../libuv/src/unix/android-ifaddrs.c
             ../libuv/src/unix/async.c
             ../libuv/src/unix/core.c
             ../libuv/src/unix/dl.c
             ../libuv/src/unix/fs.c
             ../libuv/src/unix/getaddrinfo.c
             ../libuv/src/unix/getnameinfo.c
             ../libuv/src/unix/linux-core.c
             ../libuv/src/unix/linux-inotify.c
             ../libuv/src/unix/linux-syscalls.c
             ../libuv/src/unix/loop-watcher.c
             ../libuv/src/unix/loop.c
             ../libuv/src/unix/no-fsevents.c
             ../libuv/src/unix/no-proctitle.c
             ../libuv/src/unix/pipe.c
             ../libuv/src/unix/poll.c
             ../libuv/src/unix/process.c
             ../libuv/src/unix/procfs-exepath.c
             ../libuv/src/unix/proctitle.c
             ../libuv/src/unix/pthread-fixes.c
             ../libuv/src/unix/signal.c
             ../libuv/src/unix/stream.c
             ../libuv/src/unix/sysinfo-loadavg.c
             ../libuv/src/unix/tcp.c
             ../libuv/src/unix/thread.c
             ../libuv/src/unix/timer.c
             ../libuv/src/unix/tty.c
             ../libuv/src/unix/udp.c
             ../libuv/src/fs-poll.c
             ../libuv/src/inet.c
             ../libuv/src/threadpool.c
             ../libuv/src/uv-common.c
             ../libuv/src/version.c )

# nettle

execute_process(COMMAND sh .bootstrap WORKING_DIRECTORY ../nettle)
execute_process(COMMAND sh ./configure --disable-assembler WORKING_DIRECTORY ../nettle)

if(ARMEABI)
    add_definitions(-DSIZEOF_LONG=4)
elseif(ARMEABI_V7A)
    add_definitions(-DSIZEOF_LONG=4)
elseif(ARM64_V8A)
    add_definitions(-DSIZEOF_LONG=8)
elseif(X86)
    add_definitions(-DSIZEOF_LONG=4)
elseif(X86_64)
    add_definitions(-DSIZEOF_LONG=8)
elseif(MIPS)
    add_definitions(-DSIZEOF_LONG=4)
elseif(MIPS64)
    add_definitions(-DSIZEOF_LONG=8)
endif()

add_library( # Sets the name of the library.
             nettle

             # Sets the library as a static library.
             STATIC

             # Provides a relative path to source file(s).
             ../nettle/aes-encrypt.c
             ../nettle/aes-encrypt-internal.c
             ../nettle/aes-encrypt-table.c
             ../nettle/aes-set-key-internal.c
             ../nettle/aes256-set-encrypt-key.c
             ../nettle/base16-decode.c
             ../nettle/base16-encode.c
             ../nettle/base64-decode.c
             ../nettle/base64-encode.c
             ../nettle/ctr.c
             ../nettle/gcm.c
             ../nettle/gcm-aes256.c
             ../nettle/hmac.c
             ../nettle/hmac-sha256.c
             ../nettle/hmac-sha512.c
             ../nettle/memxor.c
             ../nettle/memxor3.c
             ../nettle/pbkdf2.c
             ../nettle/pbkdf2-hmac-sha256.c
             ../nettle/ripemd160.c
             ../nettle/ripemd160-compress.c
             ../nettle/sha256.c
             ../nettle/sha256-compress.c
             ../nettle/sha256-meta.c
             ../nettle/sha512.c
             ../nettle/sha512-compress.c
             ../nettle/sha512-meta.c
             ../nettle/version.c
             ../nettle/write-be32.c
             ../nettle/write-le32.c )

# Directories with headers

include_directories( include/
                     ../libstorj/src/
                     ../
                     ../libuv/include
                     ../libuv/src
                     ../nettle )

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

target_link_libraries( storj-java storj json-c curl ssl crypto ${zlib} uv nettle ${log-lib} )