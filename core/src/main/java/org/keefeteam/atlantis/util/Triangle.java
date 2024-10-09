package org.keefeteam.atlantis.util;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Triangle {
    Vector2 p1;
    Vector2 p2;
    Vector2 p3;

    private boolean pointRightSign(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 pt) {
        if (p1.x == p2.x) {
            return (p3.x > p1.x) == (pt.x > p1.x);
        }

        float slope = (p2.y - p1.y) / (p2.x - p1.x);
        float intercept = p2.y - p2.x * slope;
        float expectedPos = pt.x * slope + intercept;
        float signExpectedPos = p3.x * slope + intercept;

        return (pt.x > expectedPos) == (p3.x > signExpectedPos);
    }

    public boolean pointInTriangle(Vector2 p) {
        return pointRightSign(p1, p2, p3, p) && pointRightSign(p2, p3, p1, p) && pointRightSign(p3, p1, p2, p);
    }

    public boolean triangleInside(Triangle t) {
        return t.pointInTriangle(p1)  || t.pointInTriangle(p2) || t.pointInTriangle(p3);
    }

    public boolean triangleOverlap(Triangle t) {
        return triangleInside(t) || t.triangleInside(this);
    }
}
