package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import org.keefeteam.atlantis.util.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.collision.Collider;
import org.keefeteam.atlantis.util.input.InputEvent;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tilemap implements Entity, Collider, Renderable {
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
        Tile fauxTile = new Tile(new ArrayList<>(), null);
        return tiles.getOrDefault(coordFromVector2(point), fauxTile);

    }

    public boolean collidesWith(Triangle tri) {
        Tile t1 = getTileFromVector2(tri.getP1());
        Tile t2 = getTileFromVector2(tri.getP2());
        Tile t3 = getTileFromVector2(tri.getP3());

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
    public Set<ColliderTypes> getColliderTypes() {
        return Set.of(ColliderTypes.WALL);
    }

    @Override
    public void update(GameState gameState, Set<InputEvent> events) {

    }


    @Override
    public void render(SpriteBatch batch) {
        for (TileCoordinate key : tiles.keySet()) {
            batch.draw(tiles.get(key).getTexture(), key.toWorldCoordinate().getCoord().x, key.toWorldCoordinate().getCoord().y);
        }
    }
}
