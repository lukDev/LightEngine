/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects;

import lightEngine.core.ObjectController;
import lightEngine.gameObjects.modules.Module;
import lightEngine.gameObjects.modules.physics.MovementModule;
import lightEngine.gameObjects.modules.renderable.ModuleRenderable;
import lightEngine.gameObjects.modules.renderable.RenderModule;
import lightEngine.util.math.vectors.Matrix3f;
import lightEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

public class GameObject implements Serializable {

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f percentRotation;
    public List<Module> modules = new ArrayList<Module>();

    private long uuid = UUID.randomUUID().getMostSignificantBits();
    private BoundingBox boundingBox;

    /**
     * The default constructor for game objects
     *
     * @param pos The initial position of the object
     * @param rot The initial rotation
     */
    public GameObject(Vector3f pos, Vector3f rot) {

        position = pos;
        rotation = rot;

        percentRotation = new Vector3f(0, 0, -1);

        if (!VectorHelper.areEqual(rotation, new Vector3f())) {

            Matrix3f xAxisRotationMatrix = new Matrix3f(
              new Vector3f(1, 0, 0),
              new Vector3f(0, (float) Math.cos(Math.toRadians(-rotation.x)), (float) -Math.sin(Math.toRadians(-rotation.x))),
              new Vector3f(0, (float) Math.sin(Math.toRadians(-rotation.x)), (float) Math.cos(Math.toRadians(-rotation.x))));
            percentRotation = xAxisRotationMatrix.multiplyByVector(percentRotation);

            Matrix3f yAxisRotationMatrix = new Matrix3f(
              new Vector3f((float) Math.cos(Math.toRadians(rotation.y)), 0, (float) Math.sin(Math.toRadians(rotation.y))),
              new Vector3f(0, 1, 0),
              new Vector3f((float) -Math.sin(Math.toRadians(rotation.y)), 0, (float) Math.cos(Math.toRadians(rotation.y))));
            percentRotation = yAxisRotationMatrix.multiplyByVector(percentRotation);

        }

        boundingBox = new BoundingBox(new Vector3f(), new Vector3f()); //Empty bounding box to prevent null references

        ObjectController.addGameObject(this);

    }

    /**
     * This constructor is used to clone existing game objects
     *
     * @param src The game object to clone
     */
    public GameObject(GameObject src) {

        position = src.position;
        rotation = src.rotation;
        percentRotation = src.percentRotation;

        src.modules.forEach(this::addModule);

        ObjectController.addGameObject(this);
    }

    /**
     * Automatically called by the game loop, updates the object and its modules
     */
    public void update() { modules.forEach(Module::onUpdate); }

    /**
     * This is used for preparing the game object for serialization (saving it in its current state)
     * It also takes care of all of the game objects modules
     */
    public void save() {
        modules.forEach(Module::onSave);
    }

    /**
     * This prepares the game object and its components for de-serialization (loading)
     */
    public void load() {
        modules.forEach(Module::onSave);
    }

    /**
     * Allows for easier identification of game objects
     *
     * @return The Universally Unique Identifier of the game object
     */
    public long getUuid() {
        return uuid;
    }

    /**
     * Adds the game object and its modules to the current render queue.
     * This method is called by the render loop
     */
    public void addToRenderQueue() {
        //Adds this gameObject's models, particles and guiElements to the renderQueue
        modules.stream()
          .filter(module -> module instanceof ModuleRenderable)
          .forEach(module -> ((ModuleRenderable) module).addToRenderQueue());
    }

    /**
     * Adds the desired module to the game object's module list
     *
     * @param module The desired module
     * @return The game object, allows for chaining module additions
     */
    public GameObject addModule(Module module) {
        modules.add(module);
        return this;
    }

    /**
     * Removes the first module of the given class from the game object
     *
     * @param moduleClass The module's class
     */
    public void removeModule(Class moduleClass) {
        Module module = getModule(moduleClass);

        module.onDestroy();
        modules.remove(module);
    }

    /**
     * Returns the first module of the given class in the game object's module list
     *
     * @param moduleClass The class of the desired module
     * @return The first module with the desired class or null if no instance of this class is available
     */
    public Module getModule(Class moduleClass) {
        try {
            return modules.stream().filter(moduleClass::isInstance).findAny().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Executes onDestroy() in all modules and removes the game object from the list
     */
    public void destroy() {
        modules.forEach(Module::onDestroy);
        ObjectController.gameObjects.remove(this);
    }

    /**
     * This must be executed after all modules were added to the game object.
     * It calls the modules' onCreation() methods
     *
     * @return The game objects, allows for adding it to the object controller after executing this method
     */
    public GameObject createModules() {

        //Multiple for-loops because every module has to be in the gameObject's list first because of their dependencies
        List<Module> modules = this.modules.stream().collect(Collectors.toList());

        modules.stream()
          .filter(module -> module instanceof RenderModule
          ).forEach(module -> module.onCreation(this));

        modules.stream()
          .filter(module -> module instanceof MovementModule)
          .forEach(module -> module.onCreation(this));

        modules.stream()
          .filter(module -> !(module instanceof RenderModule || module instanceof MovementModule))
          .forEach(module -> module.onCreation(this));

        return this;

    }

    /**
     * Returns the bounding box of this game object (around all modules that have a bounding box)
     *
     * @return The bounding box
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * Updates the game object bounding box, so it surrounds all modules.
     *
     * @param newBox The box to add (the game object bounding box is going to expand automatically)
     */
    public void addBoundingBox(BoundingBox newBox) {

        if (newBox.a.x < boundingBox.a.x) boundingBox.a.x = newBox.a.x;
        if (newBox.a.y < boundingBox.a.y) boundingBox.a.y = newBox.a.y;
        if (newBox.a.z < boundingBox.a.z) boundingBox.a.z = newBox.a.z;

        if (newBox.b.x > boundingBox.b.x) boundingBox.b.x = newBox.b.x;
        if (newBox.b.y > boundingBox.b.y) boundingBox.b.y = newBox.b.y;
        if (newBox.b.z > boundingBox.b.z) boundingBox.b.z = newBox.b.z;

    }

}