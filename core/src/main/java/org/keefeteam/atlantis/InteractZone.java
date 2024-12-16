package org.keefeteam.atlantis;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
public class InteractZone implements Collider, Entity {

    @FunctionalInterface
    interface InteractFunction {
        void call(GameState state, Player player);
    }

    WorldCoordinate position;
    List<Triangle> triangles;
    InteractFunction onInteract;

    public List<Triangle> getTris() {
        return getTris(position.getCoord());
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
    public void update(GameState gameState, List<InputEvent> events) {

    }

}
