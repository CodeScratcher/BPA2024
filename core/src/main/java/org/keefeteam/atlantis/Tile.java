package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.keefeteam.atlantis.coordinates.TileCoordinate;
import org.keefeteam.atlantis.util.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Tile {
    private List<Triangle> colliders;
    private Texture texture;

    public List<Triangle> getTriangles(TileCoordinate tileCoordinate) {
        Vector2 basis = tileCoordinate.toWorldCoordinate().getCoord();

        return colliders.
            stream()
            .map(tri -> new Triangle(tri.getP1().add(basis), tri.getP2().add(basis), tri.getP3().add(basis))).toList();
    }
}
