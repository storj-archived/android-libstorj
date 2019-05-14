/*
 * Copyright (C) 2017 Kaloyan Raev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.storj.libstorj.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Objects;

import io.storj.libstorj.Storj;

/**
 * Factory for creating new {@link Storj} instances, ready for use on Android.
 */
public class StorjAndroid {

    private static final String TAG = "StorjAndroid";

    private static Storj instance = null;
    private static String bridgeUrl = null;
    private static boolean initialized = false;

    private StorjAndroid() {
    }

    /**
     * Returns an instance of the {@link Storj} class.
     *
     * <p>The returned instance will be an existing instance when possible.</p>
     *
     * <p>Calling this method compared to the {@link Storj} constructor will do the required
     * initialization for the io.storj.libstorj library to work correctly on Android. For example:</p>
     * <ul>
     *     <li>Set the app's internal data directory as the configuration directory</li>
     *     <li>Set the Android Downloads directory as the default download location for files</li>
     *     <li>Set the app's cache directory as the temp directory</li>
     *     <li>Copy a CA Certs file to the device and configure io.storj.libstorj to use it</li>
     * </ul>
     *
     * @param context a reference to {@link Context} to retrieve info about the app environment
     * @return a {@link Storj} instance, never null
     */
    public static Storj getInstance(Context context, String bridgeUrl) throws MalformedURLException {
        synchronized (StorjAndroid.class) {
            if (instance == null || !Objects.equals(bridgeUrl, StorjAndroid.bridgeUrl)) {
                if (bridgeUrl == null) {
                    instance = new Storj();
                } else {
                    instance = new Storj(bridgeUrl);
                }
                StorjAndroid.bridgeUrl = bridgeUrl;
                initialize(context);
            }
        }
        return instance;
    }

    private static void initialize(Context context) {
        if (!initialized) {
            setConfigDir(context);
            setDownloadDir();
            setTempDir(context);
            initialized = true;
        }
    }

    /*
     * The config directory is where the authentication files with encryption keys are created.
     * Usually this is done in the user's home directory. However, there is no such user's home
     * directory on the Android platform. Instead there is a separate data directory for each
     * Android application where it can write internal data files.
     */
    private static void setConfigDir(Context context) {
        instance.setConfigDirectory(context.getFilesDir());
    }

    /*
     * The download directory is the default location for downloading files. The factory sets it to
     * the Android Downloads directory.
     */
    private static void setDownloadDir() {
        instance.setDownloadDirectory(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    /*
     * The sharding process in io.storj.libstorj requires the usage of the temp directory. However, there is
     * no global temp directory in the Android platform. The Android app should set the temp
     * directory to the app's cache directory using the STORJ_TEMP environment variable.
     */
    private static void setTempDir(Context context) {
        try {
            Os.setenv("STORJ_TEMP", context.getCacheDir().getPath(), true);
        } catch (ErrnoException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

}
