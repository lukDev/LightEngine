/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.resources;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResourceHelper {

    public static final int RES_GENERIC = 0;

    public static final int RES_ASSET = 1;
    public static final int RES_PREFERENCE = 2;
    public static final int RES_SAVE = 3;

    public static final int RES_MATERIAL = 4;
    public static final int RES_MODEL = 5;
    public static final int RES_SOUND = 6;
    public static final int RES_TEXTURE = 7;
    public static final int RES_TEXTURE_ANIMATED = 8;
    public static final int RES_SHADER_V = 9;
    public static final int RES_SHADER_F = 10;

    private static List<String> paths = new ArrayList<String>();

    /**
     * Establishes all resource path variable
     */
    public static void initialize() {

        paths.add("res/"); //RES_GENERIC

        paths.add(paths.get(0) + "assets/"); //RES_ASSET
        paths.add(paths.get(0) + "preferences/"); //RES_PREFERENCE
        paths.add(paths.get(0) + "saves/"); //RES_SAVE

        paths.add(paths.get(1) + "materials/"); //RES_MATERIAL
        paths.add(paths.get(1) + "models/"); //RES_MODEL
        paths.add(paths.get(1) + "sounds/"); //RES_SOUND
        paths.add(paths.get(1) + "textures/"); //RES_TEXTURE
        paths.add(paths.get(1) + "textures/"); //RES_TEXTURE_ANIMATED
        paths.add(paths.get(1) + "shaders/"); //RES_SHADER_V
        paths.add(paths.get(1) + "shaders/"); //RES_SHADER_F

    }

    /**
     * Returns a game resource.
     *
     * @param fileName The name of the requested resource, without file ending.
     * @param type     The resource type (ResourceHelper.RES_XXX).
     * @return The requested resource file.
     */
    public static File getResource(String fileName, int type) {

        String filePath;

        switch (type) {

            default:
                filePath = paths.get(type) + fileName;
                break;
            case RES_PREFERENCE:
                filePath = paths.get(type) + fileName + ".properties";
                break;
            case RES_SAVE:
                filePath = paths.get(type) + fileName + ".mess";
                break;
            case RES_MATERIAL:
                filePath = paths.get(type) + fileName + ".mtl";
                break;
            case RES_MODEL:
                filePath = paths.get(type) + fileName + ".obj";
                break;
            case RES_SOUND:
                filePath = paths.get(type) + fileName + ".wav";
                break;
            case RES_TEXTURE:
                filePath = paths.get(type) + fileName + ".png";
                break;
            case RES_TEXTURE_ANIMATED:
                filePath = paths.get(type) + fileName + ".meta";
                break;
            case RES_SHADER_V:
                filePath = paths.get(type) + fileName + ".vs";
                break;
            case RES_SHADER_F:
                filePath = paths.get(type) + fileName + ".fs";
                break;

        }

        return new File(filePath);

    }

    /**
     * Returns the URL to a requested resource.
     *
     * @param fileName The name of the requested resource, without file ending.
     * @param type     The resource type (ResourceHelper.RES_XXX).
     * @return The requested URL.
     */
    public static URL getResourceURL(String fileName, int type) {

        File file = getResource(fileName, type);
        URL url = null;

        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

}
