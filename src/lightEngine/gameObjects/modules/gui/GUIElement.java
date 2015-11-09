/*
 * Copyright (c) 2015 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects.modules.gui;

import lightEngine.gameObjects.modules.gui.modules.GUIModule;
import lightEngine.gameObjects.modules.renderable.ModuleRenderable;
import lightEngine.graphics.Renderer;
import lightEngine.graphics.renderable.materials.Material2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

public class GUIElement extends ModuleRenderable {

    public Material2D material;
    public List<GUIModule> components = new ArrayList<>();
    private Vector2f position; //Values from 0 to 1
    private Vector2f size; //Values from 0 to 1

    public GUIElement(Vector2f posInPixels) {
        this(posInPixels, new Vector2f());
    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels) {
        this(posInPixels, sizeInPixels, Color.white);
    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, String textureName) {

        material = new Material2D();
        material.setTextureName(textureName);

        //Absolute position to relative position
        position = new Vector2f(
          posInPixels.x / Display.getWidth(),
          posInPixels.y / Display.getHeight()
        );

        //Absolute size to relative size
        size = new Vector2f(
          sizeInPixels.x / Display.getWidth(),
          sizeInPixels.y / Display.getHeight()
        );

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, Color color) {

        material = new Material2D();
        material.setColor(new Color(color));

        //Absolute size to relative size
        position = new Vector2f();
        position.x = posInPixels.x / Display.getWidth();
        position.y = posInPixels.y / Display.getHeight();

        //Absolute size to relative size
        size = new Vector2f();
        size.x = sizeInPixels.x / Display.getWidth();
        size.y = sizeInPixels.y / Display.getHeight();

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        components.forEach(lightEngine.gameObjects.modules.gui.modules.GUIModule::onUpdate);
    }

    @Override
    public void onSave() {
        super.onSave();
        components.forEach(lightEngine.gameObjects.modules.gui.modules.GUIModule::onSave);

        material.deleteTexture();
        material.deleteColor();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        components.forEach(lightEngine.gameObjects.modules.gui.modules.GUIModule::onLoad);
    }

    public GUIElement addModule(GUIModule module) {
        components.add(module);
        module.onCreation(this);
        return this;
    }

    public GUIElement setMaterial(Material2D material) {
        this.material = material;
        return this;
    }

    public void render() {
        if (material.getTexture() == null && material.hasTexture())
            material.setTextureFromName();

        components.forEach(lightEngine.gameObjects.modules.gui.modules.GUIModule::render);
    }

    @Override
    public void addToRenderQueue() {
        Renderer.currentRenderQueue.addGUIElement(this);
    }

    public Vector2f getSize() {
        return new Vector2f(size.x * Display.getWidth(), size.y * Display.getHeight());
    }

    public void setSize(Vector2f sizeInPixels) {
        size = new Vector2f(sizeInPixels.x / Display.getWidth(), sizeInPixels.y / Display.getHeight());
    }

    public Vector2f getPosition() {
        return new Vector2f(position.x * Display.getWidth(), position.y * Display.getHeight());
    }

    public void setPosition(Vector2f positionInPixels) {
        position = new Vector2f(positionInPixels.x / Display.getWidth(), positionInPixels.y / Display.getHeight());
    }

}
