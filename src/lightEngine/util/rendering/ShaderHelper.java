/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.rendering;

import lightEngine.util.resources.ResourceHelper;
import lightEngine.util.threading.ThreadHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderHelper {

    public static Map<String, Integer> shaderPrograms = new HashMap<>();

    /**
     * Adds a shader to the list.
     *
     * @param fileName The name of the shader.
     */
    public static void addShader(String fileName) {

        int shaderProgram = glCreateProgram();

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        StringBuilder vertexShaderSource = new StringBuilder();
        StringBuilder fragmentShaderSource = new StringBuilder();

        try {

            BufferedReader vertexReader = new BufferedReader(new FileReader(ResourceHelper.getResource(fileName, ResourceHelper.RES_SHADER_V)));
            BufferedReader fragmentReader = new BufferedReader(new FileReader(ResourceHelper.getResource(fileName, ResourceHelper.RES_SHADER_F)));
            String line;

            while ((line = vertexReader.readLine()) != null) {
                vertexShaderSource.append(line + "\n");
            }

            vertexReader.close();

            while ((line = fragmentReader.readLine()) != null) {
                fragmentShaderSource.append(line + "\n");
            }

            fragmentReader.close();

        } catch (IOException e) {

            e.printStackTrace();
            ThreadHelper.stopAllThreads();
            System.exit(1);

        }

        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);

        glLinkProgram(shaderProgram);
        glValidateProgram(shaderProgram);

        shaderPrograms.put(fileName, shaderProgram);

        glDeleteShader(fragmentShader);
        glDeleteShader(vertexShader);

    }

    /**
     * Sets the desired shader active.
     *
     * @param shaderName The desired shader.
     */
    public static void useShader(String shaderName) {

        glUseProgram(shaderPrograms.get(shaderName));

    }

    /**
     * Use no shader. Oh, really...?
     */
    public static void useNoShader() {

        glUseProgram(0);

    }

}
