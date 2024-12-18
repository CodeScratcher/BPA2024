package org.keefeteam.atlantis.util.collision;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

@Getter
public class Quadrilateral {
    Vector2 p1;
    Vector2 p2;
    Vector2 p3;
    Vector2 p4;

    public boolean pointInQuad(Vector2 pt) {
        return getT1().pointInTriangle(pt) || getT2().pointInTriangle(pt);
    }


    public Triangle getT1() {
        return new Triangle(p1, p2, p3);
    }

    public Triangle getT2() {
        return new Triangle(p2, p3, p4);
    }

    public boolean quadOverlap(Quadrilateral q) {
        return getT1().triangleOverlap(getT1()) ||
            getT2().triangleOverlap(q.getT1()) ||
            getT1().triangleOverlap(q.getT2()) ||
            getT2().triangleOverlap(q.getT2());
    }
}
