package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.List;

@Getter
@AllArgsConstructor
public class Tile {
    private final List<Triangle> colliders;
    private Texture texture;

    public List<Triangle> getTriangles(TileCoordinate tileCoordinate) {
        System.out.println(colliders);
        Vector2 basis = tileCoordinate.toWorldCoordinate().getCoord();

        return colliders.
            stream()
            .map(tri -> new Triangle(tri.getP1().cpy().add(basis), tri.getP2().cpy().add(basis), tri.getP3().cpy().add(basis))).toList();
    }
}
