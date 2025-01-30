package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.collision.Collider;
import org.keefeteam.atlantis.util.input.InputEvent;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.List;
import java.util.Set;

import org.keefeteam.atlantis.util.coordinates.Coordinate;

/**
 * A zone with a hitbox that the player can interact with
 */
@Getter
@Setter
@AllArgsConstructor
public class InteractZone implements Collider, Entity {

    /**
     * Specialized biconsumer
     */
    @FunctionalInterface
    public interface InteractFunction {
        /**
         * On interact function
         * @param state Game state as of interaction
         * @param player Player who interacted
         */
        void call(GameState state, Player player);
    }


    /**
     * Position of interact zone
     */
    private Coordinate position;

    /**
     * Hitbox of interact zone, coordinates relative to position
     */
    private List<Triangle> triangles;

    /**
     * What the interact zone does
     */
    private InteractFunction onInteract;

    /**
     * Get the hitbox, but shifted to be in world coordinates
     * @return The hitbox of the interact zone, adjusted to world view
     */
    public List<Triangle> getTris() {
        return getTris(position.toWorldCoordinate().getCoord());
    }

    /**
     * Get the hitbox, with origin moved to some basis
     * @param basis Where the hitbox is located
     * @return The hitbox shifted to that basis
     */
    public List<Triangle> getTris(Vector2 basis) {
        List<Triangle> tris = triangles.stream().map(tri -> new Triangle(tri.getP1().cpy().add(basis), tri.getP2().cpy().add(basis), tri.getP3().cpy().add(basis))).toList();
        return tris;
    }

    /**
     * Checks if the interact zone's hitbox collides with some list of triangles (both specified in world coordinates)
     * @param tris The hitbox the interact zone may overlap with
     * @return Whether the hitbox collides with the given triangles
     */
    @Override
    public boolean collidesWith(List<Triangle> tris) {
        for (Triangle tri : getTris()) {
            for (Triangle tri2 : tris) {
                if (tri.triangleOverlap(tri2)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Set<ColliderTypes> getColliderTypes() {
        return Set.of(ColliderTypes.INTERACTABLE);
    }

    @Override
    public void update(GameState gameState, Set<InputEvent> events) {

    }

}
