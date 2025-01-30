package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.List;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
/**
 * A tile in a tilemap
 */
public class Tile {
    /**
     * The hitbox of the tile
     */
    private List<Triangle> colliders;

    /**
     * Get the tile's collision relative to its position
     * @param tileCoordinate The position of the tile
     * @return A list of triangles representing the tile's collision
     */
    public List<Triangle> getTriangles(TileCoordinate tileCoordinate) {
        Vector2 basis = tileCoordinate.toWorldCoordinate().getCoord();

        return colliders.
            stream()
            .map(tri -> new Triangle(tri.getP1().cpy().add(basis), tri.getP2().cpy().add(basis), tri.getP3().cpy().add(basis))).toList();
    }
}
