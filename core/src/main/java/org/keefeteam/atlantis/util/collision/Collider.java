package org.keefeteam.atlantis.util.collision;

import java.util.List;
import java.util.Set;

/**
 * An interface for something that can collide with something else
 */
public interface Collider {
    /**
     * What the object is
     */
    enum ColliderTypes {
        WALL,
        ENEMY,
        INTERACTABLE
    }

    /**
     * Check if the collider collides with a list of triangles
     * @param tris A hitbox
     * @return Whether there is a collision
     */

    boolean collidesWith(List<Triangle> tris);

    /**
     * Gets the collider type of the object
     * @return A set containing all collision types
     */
    Set<ColliderTypes> getColliderTypes();
}
