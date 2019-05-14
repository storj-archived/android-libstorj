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
package io.storj.libstorj;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for getting version info about the dependent native libraries
 * used at runtime.
 */
public class NativeLibraries {

    public static Map<String, String> getVersions() {
        Map<String, String> map = new HashMap<>();
        map.put("JSON-C", getJsonCVersion());
        map.put("cURL", getCurlVersion());
        map.put("libuv", getLibuvVersion());
        map.put("Nettle", getNettleVersion());
        return map;
    }

    /**
     * Returns the version of the json-c library.
     *
     * @return a version string
     *
     * @deprecated This method is not supported anymore. There is no replacement.
     */
    public static String getJsonCVersion() {
        return "0.0.0-deprecated";
    }

    /**
     * Returns the version of the cURL library.
     * 
     * @return a version string
     *
     * @deprecated This method is not supported anymore. There is no replacement.
     */
    public static String getCurlVersion() {
        return "0.0.0-deprecated";
    }

    /**
     * Returns the version of the libuv library.
     * 
     * @return a version string
     *
     * @deprecated This method is not supported anymore. There is no replacement.
     */
    public static String getLibuvVersion() {
        return "0.0.0-deprecated";
    }

    /**
     * Returns the version of the Nettle library.
     * 
     * @return a version string
     *
     * @deprecated This method is not supported anymore. There is no replacement.
     */
    public static String getNettleVersion() {
        return "0.0.0-deprecated";
    }

}
