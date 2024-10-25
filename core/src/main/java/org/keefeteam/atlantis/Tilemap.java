package org.keefeteam.atlantis;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keefeteam.atlantis.coordinates.TileCoordinate;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.Triangle;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tilemap implements Entity {
    private Map<TileCoordinate, Tile> tiles = new HashMap<>();

    public void addTiles(TileCoordinate coord, Tile tile) {
        tiles.put(coord, tile);
    }

    private TileCoordinate coordFromVector2(Vector2 v2) {
        TileCoordinate tc = new TileCoordinate(0, 0);
        tc.fromWorldCoordinate(new WorldCoordinate(v2));
        return tc;
    }

    public Tile getTileFromVector2(Vector2 point) {
        return tiles.get(coordFromVector2(point));

    }

    public boolean collidesWith(Triangle tri) {
        Tile t1 = getTileFromVector2(tri.getP1());
        Tile t2 = getTileFromVector2(tri.getP1());
        Tile t3 = getTileFromVector2(tri.getP1());

        Set<Triangle> tris = new HashSet<>();

        for (Triangle collider : t1.getTriangles(coordFromVector2(tri.getP1()))) {
            if (tris.contains(collider)) continue;
            tris.add(collider);
            if (collider.triangleOverlap(tri)) return true;
        }

        for (Triangle collider : t2.getTriangles(coordFromVector2(tri.getP2()))) {
            if (tris.contains(collider)) continue;
            tris.add(collider);
            if (collider.triangleOverlap(tri)) return true;
        }

        for (Triangle collider : t3.getTriangles(coordFromVector2(tri.getP3()))) {
            if (tris.contains(collider)) continue;
            tris.add(collider);
            if (collider.triangleOverlap(tri)) return true;
        }

        return false;
    }

    public boolean collidesWith(List<Triangle> tris) {
        for (Triangle tri : tris) {
            if (collidesWith(tri)) return true;
        }

        return false;
    }

    @Override
    public void update(GameState gameState, List<InputEvent> events) {

    }


}
