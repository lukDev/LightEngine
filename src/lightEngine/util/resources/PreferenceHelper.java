/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PreferenceHelper {

    private static Properties properties = new Properties();

    /**
     * Loads a preference file.
     *
     * @param fileName The preference file to load.
     */
    public static void loadPreferences(String fileName) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(ResourceHelper.getResource(fileName, ResourceHelper.RES_PREFERENCE)));
            properties.load(reader);
            reader.close();

        } catch (IOException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    /**
     * Write a preference to the opened preference file.
     *
     * @param key   The name of the preference.
     * @param value The preference value.
     */
    public static void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Retrieves a value from the loaded preference file.
     *
     * @param key The name of the preference.
     * @return The preference value.
     */
    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves a boolean from the loaded preference file.
     *
     * @param key The name of the preference.
     * @return The preference value as boolean.
     */
    public static boolean getBoolean(String key) {

        String value = getValue(key);
        return value.toLowerCase().equals("true") || value.equals("1");

    }

    /**
     * Retrieves an integer from the loaded preference file.
     *
     * @param key The name of the preference.
     * @return The preference value as integer.
     */
    public static int getInteger(String key) {
        return Integer.valueOf(getValue(key));
    }

    /**
     * Retrieves a float from the loaded preference file.
     *
     * @param key The name of the preference.
     * @return The preference value as float.
     */
    public static float getFloat(String key) {
        return Float.valueOf(getValue(key));
    }

}
