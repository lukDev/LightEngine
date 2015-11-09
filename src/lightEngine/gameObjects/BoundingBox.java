/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.gameObjects;

import org.lwjgl.util.vector.Vector3f;

public class BoundingBox {

    Vector3f a, b;

    /**
     * Creates an AABB (Axis Aligned Bounding Box) for a game object
     *
     * @param a The first corner (e.g. lower back left)
     * @param b The second corner (e.g. upper front right), has to be the opposite corner to a
     */
    public BoundingBox(Vector3f a, Vector3f b) {

        this.a = new Vector3f();
        this.b = new Vector3f();

        //A is going to be the "smaller" point and b the "bigger" one
        sortCoordinates(a, b);

    }

    public Vector3f getA() {
        return a;
    }

    public Vector3f getB() {
        return b;
    }

    /**
     * Lets you update the bounding box
     *
     * @param a Point a
     * @param b Point b
     */
    public void setBox(Vector3f a, Vector3f b) {
        sortCoordinates(a, b);
    }

    /**
     * Sorts the coordinates, so a is more in the negative direction than b
     *
     * @param a Point a
     * @param b Point b
     */
    private void sortCoordinates(Vector3f a, Vector3f b) {

        //Sorting x coords, so point a is the smaller one
        if (a.x < b.x) {
            this.a.x = a.x;
            this.b.x = b.x;
        } else {
            this.a.x = b.x;
            this.b.x = a.x;
        }

        //Sorting y coords, so point a is the smaller one
        if (a.y < b.y) {
            this.a.y = a.y;
            this.b.y = b.y;
        } else {
            this.a.y = b.y;
            this.b.y = a.y;
        }

        //Sorting z coords, so point a is the smaller one
        if (a.y < b.y) {
            this.a.y = a.y;
            this.b.y = b.y;
        } else {
            this.a.y = b.y;
            this.b.y = a.y;
        }

    }

    @Override
    public String toString() {
        return a.toString() + " | " + b.toString();
    }
}
