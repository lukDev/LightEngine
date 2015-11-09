/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package lightEngine.util.math.vectors;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;

public class VectorHelper {

    public static Vector3f negateVector(Vector3f vector) {
        return multiplyVectorByFloat(vector, -1);
    }

    public static Vector3f normalizeVector(Vector3f vector) {
        return divideVectorByFloat(vector, getAbs(vector));
    }

    public static Vector3f sumVectorAndFloat(Vector3f vector, float addend) {
        return sumVectors(new Vector3f[]{vector, new Vector3f(addend, addend, addend)});
    }

    public static Vector3f subtractVectorAndFloat(Vector3f vector, float subtrahend) {
        return subtractVectors(vector, new Vector3f(subtrahend, subtrahend, subtrahend));
    }

    public static Vector3f multiplyVectorByFloat(Vector3f vector, float multiplier) {
        return multiplyVectors(new Vector3f[]{vector, new Vector3f(multiplier, multiplier, multiplier)});
    }

    public static Vector3f divideVectorByFloat(Vector3f vector, float dividend) {
        return divideVectors(vector, new Vector3f(dividend, dividend, dividend));
    }

    public static boolean areEqual(Vector3f vectorA, Vector3f vectorB) {

        if (vectorA == null) vectorA = new Vector3f();
        if (vectorB == null) vectorB = new Vector3f();

        return vectorA.x == vectorB.x
          && vectorA.y == vectorB.y
          && vectorA.z == vectorB.z;

    }

    public static Vector3f sumVectors(Vector3f[] vectors) {

        for (int count = 0; count < vectors.length; count++) {
            if (vectors[count] == null) vectors[count] = new Vector3f();
        }

        Vector3f vectorSum = new Vector3f();

        for (Vector3f vector : vectors) {
            vectorSum.x += vector.x;
            vectorSum.y += vector.y;
            vectorSum.z += vector.z;
        }

        return vectorSum;

    }

    public static Vector3f subtractVectors(Vector3f vectorA, Vector3f vectorB) {

        if (vectorA == null) vectorA = new Vector3f();
        if (vectorB == null) vectorB = new Vector3f();

        Vector3f vectorDifference = new Vector3f();

        vectorDifference.x = vectorA.x - vectorB.x;
        vectorDifference.y = vectorA.y - vectorB.y;
        vectorDifference.z = vectorA.z - vectorB.z;

        return vectorDifference;

    }

    public static Vector3f multiplyVectors(Vector3f[] vectors) {

        for (int count = 0; count < vectors.length; count++) {
            if (vectors[count] == null) vectors[count] = new Vector3f();
        }

        Vector3f vectorProduct = new Vector3f(1, 1, 1);

        for (Vector3f vector : vectors) {
            vectorProduct.x *= vector.x;
            vectorProduct.y *= vector.y;
            vectorProduct.z *= vector.z;
        }

        return vectorProduct;

    }

    public static Vector3f divideVectors(Vector3f vectorA, Vector3f vectorB) {

        if (vectorA == null) vectorA = new Vector3f();
        if (vectorB == null) vectorB = new Vector3f();

        Vector3f vectorQuotient = new Vector3f();

        vectorQuotient.x = vectorA.x / vectorB.x;
        vectorQuotient.y = vectorA.y / vectorB.y;
        vectorQuotient.z = vectorA.z / vectorB.z;

        return vectorQuotient;

    }

    public static float getAbs(Vector3f vector) {
        if (vector == null) vector = new Vector3f();
        return (float) Math.sqrt(getScalarProduct(vector, vector));
    }

    public static float getScalarProduct(Vector3f vectorA, Vector3f vectorB) {
        if (vectorA == null) vectorA = new Vector3f();
        if (vectorB == null) vectorB = new Vector3f();
        return vectorA.x * vectorB.x + vectorA.y * vectorB.y + vectorA.z * vectorB.z;
    }

    public static Vector3f getVectorProduct(Vector3f vectorA, Vector3f vectorB) {

        if (vectorA == null) vectorA = new Vector3f();
        if (vectorB == null) vectorB = new Vector3f();

        Vector3f vectorProduct = new Vector3f();

        vectorProduct.x = vectorA.y * vectorB.z - vectorA.z * vectorB.y;
        vectorProduct.y = vectorA.z * vectorB.x - vectorA.x * vectorB.z;
        vectorProduct.z = vectorA.x * vectorB.y - vectorA.y * vectorB.x;

        return vectorProduct;

    }

    public static float getAngle(Vector3f vectorA, Vector3f vectorB) {
        return (float) Math.acos(getScalarProduct(vectorA, vectorB) / (getAbs(vectorA) * getAbs(vectorB)));
    }

    public static boolean isVectorInsideRectangle(Vector2f vector, Rectangle rectangle) {

        return vector.x > rectangle.x
          && vector.x < rectangle.x + rectangle.width
          && vector.y > rectangle.y
          && vector.y < rectangle.y + rectangle.height;

    }

    public static Vector2f negateVector(Vector2f vector) {
        return multiplyVectorByFloat(vector, -1);
    }

    public static Vector2f normalizeVector(Vector2f vector) {
        return divideVectorByFloat(vector, getAbs(vector));
    }

    public static Vector2f sumVectorAndFloat(Vector2f vector, float addend) {
        return sumVectors(new Vector2f[]{vector, new Vector2f(addend, addend)});
    }

    public static Vector2f subtractVectorAndFloat(Vector2f vector, float subtrahend) {
        return subtractVectors(vector, new Vector2f(subtrahend, subtrahend));
    }

    public static Vector2f multiplyVectorByFloat(Vector2f vector, float multiplier) {
        return multiplyVectors(new Vector2f[]{vector, new Vector2f(multiplier, multiplier)});
    }

    public static Vector2f divideVectorByFloat(Vector2f vector, float dividend) {
        return divideVectors(vector, new Vector2f(dividend, dividend));
    }

    public static boolean areEqual(Vector2f vectorA, Vector2f vectorB) {

        if (vectorA == null) vectorA = new Vector2f();
        if (vectorB == null) vectorB = new Vector2f();

        return vectorA.x == vectorB.x
          && vectorA.y == vectorB.y;

    }

    public static Vector2f sumVectors(Vector2f[] vectors) {

        for (int count = 0; count < vectors.length; count++) {
            if (vectors[count] == null) vectors[count] = new Vector2f();
        }

        Vector2f vectorSum = new Vector2f();

        for (Vector2f vector : vectors) {
            vectorSum.x += vector.x;
            vectorSum.y += vector.y;
        }

        return vectorSum;

    }

    public static Vector2f subtractVectors(Vector2f vectorA, Vector2f vectorB) {

        if (vectorA == null) vectorA = new Vector2f();
        if (vectorB == null) vectorB = new Vector2f();

        Vector2f vectorDifference = new Vector2f();

        vectorDifference.x = vectorA.x - vectorB.x;
        vectorDifference.y = vectorA.y - vectorB.y;

        return vectorDifference;

    }

    public static Vector2f multiplyVectors(Vector2f[] vectors) {

        for (int count = 0; count < vectors.length; count++) {
            if (vectors[count] == null) vectors[count] = new Vector2f();
        }

        Vector2f vectorProduct = new Vector2f(1, 1);

        for (Vector2f vector : vectors) {
            vectorProduct.x *= vector.x;
            vectorProduct.y *= vector.y;
        }

        return vectorProduct;

    }

    public static Vector2f divideVectors(Vector2f vectorA, Vector2f vectorB) {

        if (vectorA == null) vectorA = new Vector2f();
        if (vectorB == null) vectorB = new Vector2f();

        Vector2f vectorQuotient = new Vector2f();

        vectorQuotient.x = vectorA.x / vectorB.x;
        vectorQuotient.y = vectorA.y / vectorB.y;

        return vectorQuotient;

    }

    public static float getAbs(Vector2f vector) {
        if (vector == null) vector = new Vector2f();
        return (float) Math.sqrt(getScalarProduct(vector, vector));
    }

    public static float getScalarProduct(Vector2f vectorA, Vector2f vectorB) {
        if (vectorA == null) vectorA = new Vector2f();
        if (vectorB == null) vectorB = new Vector2f();
        return vectorA.x * vectorB.x + vectorA.y * vectorB.y;
    }

    public static float getAngle(Vector2f vectorA, Vector2f vectorB) {
        return (float) Math.acos(getScalarProduct(vectorA, vectorB) / (getAbs(vectorA) * getAbs(vectorB)));
    }

}
