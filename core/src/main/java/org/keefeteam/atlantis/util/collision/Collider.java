package org.keefeteam.atlantis.util.collision;

import java.util.List;
import java.util.Set;


public interface Collider {
    enum ColliderTypes {
        WALL,
        ENEMY,
        INTERACTABLE
    }

    boolean collidesWith(List<Triangle> tris);
    Set<ColliderTypes> getColliderTypes();
}
