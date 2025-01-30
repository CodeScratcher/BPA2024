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

@Getter
@Setter
@AllArgsConstructor
public class InteractZone implements Collider, Entity {

    @FunctionalInterface
    public interface InteractFunction {
        void call(GameState state, Player player);
    }

    private Coordinate position;
    private List<Triangle> triangles;
    private InteractFunction onInteract;

    public List<Triangle> getTris() {
        return getTris(position.toWorldCoordinate().getCoord());
    }

    public List<Triangle> getTris(Vector2 basis) {
        List<Triangle> tris = triangles.stream().map(tri -> new Triangle(tri.getP1().cpy().add(basis), tri.getP2().cpy().add(basis), tri.getP3().cpy().add(basis))).toList();
        return tris;
    }
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
