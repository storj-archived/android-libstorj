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
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.storj.libstorj.Storj;

/**
 * Factory for creating new {@link Storj} instances, ready for use on Android.
 */
public class StorjAndroid {

    private static final String TAG = "StorjAndroid";

    private static boolean initialized = false;

    private StorjAndroid() {
    }

    /**
     * Returns an instance of the {@link Storj} class.
     *
     * <p>The returned instance will be an existing instance when possible.</p>
     *
     * <p>Calling this method compared to {@link Storj#getInstance()} will do the required
     * initialization for the libstorj library to work correctly on Android. For example:</p>
     * <ul>
     *     <li>Set the app's internal data directory as the working directory</li>
     *     <li>Set the app's cache directory as the temp directory</li>
     *     <li>Copy a CA Certs file to the device and configure libstorj to use it</li>
     * </ul>
     *
     * @param context a reference to {@link Context} to retrieve info about the app environment
     * @return a {@link Storj} instance, never null
     */
    public static Storj getInstance(Context context) {
        synchronized (StorjAndroid.class) {
            if (!initialized) {
                initialize(context);
            }
        }
        return Storj.getInstance();
    }

    private static synchronized void initialize(Context context) {
        setAppDir(context);
        setTempDir(context);
        copyCABundle(context);
        initialized = true;
    }

    private static void setAppDir(Context context) {
        Storj.appDir = context.getFilesDir().getPath();
    }

    private static void setTempDir(Context context) {
        try {
            Os.setenv("STORJ_TEMP", context.getCacheDir().getPath(), true);
        } catch (ErrnoException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private static void copyCABundle(final Context context) {
        String filename = "cacert.pem";
        AssetManager assetManager = context.getAssets();
        try (InputStream in = assetManager.open(filename);
             OutputStream out = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            copy(in, out);
            Os.setenv("STORJ_CAINFO", context.getFileStreamPath(filename).getPath(), true);
        } catch (IOException | ErrnoException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}
