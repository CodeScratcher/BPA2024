package org.keefeteam.atlantis.util;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TriangleTest {
    @Test public void simplePointInTriangleCollision() {
        Triangle triangle = new Triangle(new Vector2(0, 0), new Vector2(0, 3), new Vector2(2, 0));
        Triangle triangle2 = new Triangle(new Vector2(1, -1), new Vector2(1, 1), new Vector2(7, -1));
        assertTrue(triangle.pointInTriangle(new Vector2(1, 1)), "Simple right triangle-point collision");
        assertTrue(triangle2.pointInTriangle(new Vector2(3, -0.5f)), "Simple right triangle-point collision - negative point");
        assertFalse(triangle.pointInTriangle(new Vector2(0, 4)),  "Simple right triangle-point collision - not in triangle");
    }

    @Test public void simpleRightTriangleCollision() {
        Triangle triangle = new Triangle(new Vector2(0, 0), new Vector2(0, 3), new Vector2(2, 0));
        Triangle triangle2 = new Triangle(new Vector2(1, -1), new Vector2(1, 1), new Vector2(7, -1));
        Triangle triangle3 = new Triangle(new Vector2(3, 0), new Vector2(-2, -2), new Vector2(3, -2));

        assertTrue(triangle.triangleOverlap(triangle2), "Simple right triangle collision");
        assertTrue(triangle2.triangleOverlap(triangle3), "Simple right triangle collision #2");
        assertFalse(triangle.triangleOverlap(triangle3), "Simple right triangle collision - no overlap");
    }

    @Test public void insidePointInTriangleCollision() {
        Triangle acuteTriangle = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        Triangle obtuseTriangle = new Triangle(new Vector2(0, 0), new Vector2(-1, 2), new Vector2(3, -1));

        assertTrue(acuteTriangle.pointInTriangle(new Vector2(0, 1)), "Acute triangle-point collision");
        assertTrue(acuteTriangle.pointInTriangle(new Vector2(-1, 2)), "Acute triangle-point collision #2");
        assertFalse(acuteTriangle.pointInTriangle(new Vector2(1, 0)), "Acute triangle-point collision - false");

        assertTrue(obtuseTriangle.pointInTriangle(new Vector2(0, 1)), "Obtuse triangle-point collision");
        assertTrue(obtuseTriangle.pointInTriangle(new Vector2(1, 0)), "Obtuse triangle-point collision #2");
        assertFalse(obtuseTriangle.pointInTriangle(new Vector2(1, -1)), "Obtuse triangle-point collision - false");
    }

    @Test public void otherTriangleCollision() {
        Triangle acuteTriangle = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        Triangle obtuseTriangle = new Triangle(new Vector2(-3, 2), new Vector2(-4, 4), new Vector2(0, 1));
        Triangle secondAcute = new Triangle(new Vector2(1, 2), new Vector2(0, 4), new Vector2(4, 3));

        assertTrue(acuteTriangle.triangleOverlap(obtuseTriangle), "Acute-obtuse");
        assertTrue(acuteTriangle.triangleOverlap(secondAcute), "Acute-acute");
        assertFalse(obtuseTriangle.triangleOverlap(secondAcute), "Obtuse-acute: no overlap");
    }

    @Test public void completelyInsideTriangles() {
        Triangle t1 = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        Triangle t2 = new Triangle(new Vector2(0, 1), new Vector2(0, 2), new Vector2(1, 2));
        Triangle t3 = new Triangle(new Vector2(1, 0), new Vector2(-1, 4), new Vector2(3, 2));

        assertTrue(t1.triangleOverlap(t2), "Completely contained");
        assertTrue(t1.triangleOverlap(t2), "No point overlap");
    }

    @Test public void onEdgePointInTriangle() {
        Triangle acuteTriangle = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        assertTrue(acuteTriangle.pointInTriangle(new Vector2(-1, 1)), "Point on edge");
        assertTrue(acuteTriangle.pointInTriangle(new Vector2(0, 0)), "Point on vertex");
    }

    @Test public void edgeCollisions() {
        Triangle triangle = new Triangle(new Vector2(0, 0), new Vector2(0, 3), new Vector2(2, 0));
        Triangle triangle1 = new Triangle(new Vector2(0, 0), new Vector2(0, 3), new Vector2(5, 0));
        Triangle triangle2 = new Triangle(new Vector2(0, 2), new Vector2(-2, 3), new Vector2(-2, 1));
        assertTrue(triangle.triangleOverlap(triangle), "Self collision");
        assertTrue(triangle.triangleOverlap(triangle1), "Collision with shared vertex");
        assertTrue(triangle.triangleOverlap(triangle2), "Collides with edge");
    }
}
