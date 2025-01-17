package org.keefeteam.atlantis.util.collision;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Triangle {
    Vector2 p1;
    Vector2 p2;
    Vector2 p3;

    private boolean pointRightSign(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 pt) {
        if (p1.x == p2.x) {
            return (p3.x > p1.x) == (pt.x > p1.x) || pt.x == p1.x;
        }

        float slope = (p2.y - p1.y) / (p2.x - p1.x);
        float intercept = p2.y - p2.x * slope;
        float expectedPos = pt.x * slope + intercept;
        float signExpectedPos = p3.x * slope + intercept;

        return (pt.y > expectedPos) == (p3.y > signExpectedPos) || pt.y == expectedPos;
    }


    public boolean lineOverlapsTriangle(Vector2 pa, Vector2 pb) {
        if (pointInTriangle(pa) || pointInTriangle(pb)) {
            return true;
        }

        boolean crossesSideOne = !pointRightSign(p1, p2, pa, pb);
        boolean crossesSideTwo = !pointRightSign(p2, p3, pa, pb);
        boolean crossesSideThree = !pointRightSign(p3, p1, pa, pb);

        if ((crossesSideOne ? 1 : 0) + (crossesSideTwo ? 1 : 0) + (crossesSideThree ? 1 : 0) < 2) {
            return false;
        }

        if (!crossesSideThree) {
            float slope = (p2.y - p3.y) / (p2.x - p3.x);
            float intercept = p3.y - p3.x * slope;

            Vector2 expectedPoint1 = new Vector2(pa.x, pa.x * slope + intercept);
            Vector2 expectedPoint2 = new Vector2(pb.x, pb.x * slope + intercept);

            return pointRightSign(p1, p2, p3, expectedPoint1) == pointRightSign(p1, p2, p3, expectedPoint2);

        }
        else if (!crossesSideTwo) {
            float slope = (p3.y - p1.y) / (p3.x - p1.x);
            float intercept = p1.y - p1.x * slope;

            Vector2 expectedPoint1 = new Vector2(pa.x, pa.x * slope + intercept);
            Vector2 expectedPoint2 = new Vector2(pb.x, pb.x * slope + intercept);

            return pointRightSign(p1, p2, p3, expectedPoint1) == pointRightSign(p1, p2, p3, expectedPoint2);
        }
        else {
            float slope = (p2.y - p3.y) / (p2.x - p3.x);
            float intercept = p3.y - p3.x * slope;

            Vector2 expectedPoint1 = new Vector2(pa.x, pa.x * slope + intercept);
            Vector2 expectedPoint2 = new Vector2(pb.x, pb.x * slope + intercept);

            return pointRightSign(p3, p1, p2, expectedPoint1) == pointRightSign(p3, p1, p2, expectedPoint2);
        }

    }

    public boolean pointInTriangle(Vector2 p) {
        return pointRightSign(p1, p2, p3, p) && pointRightSign(p2, p3, p1, p) && pointRightSign(p3, p1, p2, p);
    }

    public boolean triangleOverlap(Triangle t) {
        // return triangleInside(t) || t.triangleInside(this);
        return lineOverlapsTriangle(t.p1, t.p2) || lineOverlapsTriangle(t.p2, t.p3) || lineOverlapsTriangle(t.p3, t.p1) ||
            t.lineOverlapsTriangle(p1, p2) || t.lineOverlapsTriangle(p2, p3) || t.lineOverlapsTriangle(p3, p1);
    }

   @Override
   public String toString() {
        return p1 + " " + p2 +  " " + p3;
    }
}
