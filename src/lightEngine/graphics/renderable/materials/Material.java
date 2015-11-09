/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.graphics.renderable.materials;

import lightEngine.graphics.renderable.textures.Texture;
import lightEngine.util.rendering.TextureHelper;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

import java.io.Serializable;

public abstract class Material implements Serializable {

    public Texture texture;
    public String textureName;
    public Color color = new Color(Color.white);

    /**
     * Releases the texture from the render context, prevents textures leaking onto other objects
     */
    public void release() {
        TextureImpl.bindNone();
    }

    /**
     * Returns the material texture
     *
     * @return The texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets the material texture
     *
     * @param texture The texture to use
     * @return The material
     */
    public Material setTexture(Texture texture) {

        this.texture = texture;
        return this;

    }

    /**
     * Sets only the texture name, the texture itself has to be set via setTextureFromName()
     *
     * @param textureName The name of the texture
     * @return The material
     */
    public Material setTextureName(String textureName) {

        this.textureName = textureName;
        return this;

    }

    /**
     * Sets the material texture from the previously set name
     */
    public void setTextureFromName() {
        texture = TextureHelper.getTexture(textureName);
    }

    /**
     * Deletes the texture from the material
     */
    public void deleteTexture() {
        texture = null;
    }

    /**
     * Determines if the material has a texture
     *
     * @return True if a texture <i>name</i> is present, false if not
     */
    public boolean hasTexture() {
        return textureName != null;
    }

    /**
     * Determines if the material has a color
     *
     * @return True if the material has a color, false if not
     */
    public boolean hasColor() {
        return color != null;
    }

    /**
     * Returns the material's color
     *
     * @return The material's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the material's color
     *
     * @param color The color to use
     * @return The material
     */
    public Material setColor(Color color) {

        this.color = new Color(color);
        return this;

    }

    /**
     * Removes the color from the material
     */
    public void deleteColor() {
        setColor(null);
    }

    /**
     * Tells the Renderer to use <i>this</i> material.
     * Automatically gets unbound when another material is bound
     */
    public void bind() {
        if (texture != null) texture.getTexture().bind();
        else if (color != null) color.bind();
    }

}
