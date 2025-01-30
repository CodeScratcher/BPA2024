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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.keefeteam.atlantis.util.TiledTilemapHandler;

/**
 * The world, as represented by a map of tiles
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Tilemap implements Entity, Collider, Renderable {
    /**
     * The actual tilemap - coordinates with tiles
     */
    @NonNull private Map<TileCoordinate, Tile> tiles = new HashMap<>();
    /**
     * Doors. Each door is a list of tiles that make up the door, and this object is a list of doors
     */
    private List<List<TileCoordinate>> doors = new ArrayList<>();
    /**
     * The handler that links this to the Tiled tilemap
     */
    @NonNull private TiledTilemapHandler handler;

    /**
     * Add tiles - this is meant for adding collision exclusively, it does not affect visuals
     * @param coord
     * @param tile
     */
    public void addTiles(TileCoordinate coord, Tile tile) {
        tiles.put(coord, tile);
    }

    private TileCoordinate coordFromVector2(Vector2 v2) {
        TileCoordinate tc = new TileCoordinate(0, 0);
        tc.fromWorldCoordinate(new WorldCoordinate(v2));
        return tc;
    }

    /**
     * Get a tile from a vector2 in world space
     * @param point A point in worldspace
     * @return The tile that covers that location, or a tile with no collision if it does not exist
     */
    public Tile getTileFromVector2(Vector2 point) {
        Tile fauxTile = new Tile(new ArrayList<>());
        return tiles.getOrDefault(coordFromVector2(point), fauxTile);

    }

    /**
     * Checks if any tiles collide with a triangle
     * @param tri A triangle that may collide with the tilemap
     * @return Whether there exist tiles that collide with this point
     */

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

    /**
     * Checks if this object overlaps with a polygon
     * @param tris
     * @return Whether any triangles in the list collides
     */
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
        handler.render(batch);
    }
}
