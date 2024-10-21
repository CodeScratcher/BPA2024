package org.keefeteam.atlantis;

import org.keefeteam.atlantis.util.Triangle;

import java.util.List;

public interface Collider {
    public boolean collidesWith(List<Triangle> tris);
}
