package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import org.keefeteam.atlantis.entities.Tile;
import org.keefeteam.atlantis.entities.Tilemap;
import org.keefeteam.atlantis.util.collision.Triangle;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TilemapTest {
    @Test public void simpleTilemap() {
        Triangle t1 = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        Triangle t2 = new Triangle(new Vector2(0, 1), new Vector2(0, 2), new Vector2(1, 2));
        Triangle t3 = new Triangle(new Vector2(1, 0), new Vector2(-1, 4), new Vector2(3, 2));

        List<Triangle> tris = Arrays.asList(t2, t3);
        Tile tile = new Tile(tris, Mockito.mock(Texture.class));

        Tilemap tm = new Tilemap();
        tm.addTiles(new TileCoordinate(0, 0), tile);

        assertTrue(tm.collidesWith(t1), "Simple tilemap collision");
    }

    @Test public void wrongSpot() {
        Triangle t1 = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        Triangle t2 = new Triangle(new Vector2(0, 1), new Vector2(0, 2), new Vector2(1, 2));
        Triangle t3 = new Triangle(new Vector2(1, 0), new Vector2(-1, 4), new Vector2(3, 2));

        List<Triangle> tris = Arrays.asList(t2, t3);
        Tile tile = new Tile(tris, Mockito.mock(Texture.class));

        Tilemap tm = new Tilemap();
        tm.addTiles(new TileCoordinate(0, 1), tile);

        assertFalse(tm.collidesWith(t1));
    }

    @Test public void multipleTiles() {
        Triangle t1 = new Triangle(new Vector2(0, 0), new Vector2(-2, 2), new Vector2(3, 3));
        Triangle t2 = new Triangle(new Vector2(0, 1), new Vector2(0, 2), new Vector2(1, 2));
        Triangle t3 = new Triangle(new Vector2(1, 0), new Vector2(-1, 4), new Vector2(3, 2));

        List<Triangle> tris = Arrays.asList(t2, t3);
        Tile tile = new Tile(tris, Mockito.mock(Texture.class));

        Tilemap tm = new Tilemap();
        tm.addTiles(new TileCoordinate(0, 0), tile);
        tm.addTiles(new TileCoordinate(0, 1), tile);
        tm.addTiles(new TileCoordinate(1, 1), tile);

        assertTrue(tm.collidesWith(t1), "Multiple tiles");

        List<Triangle> tris2 = Arrays.asList(t1, t2);

        assertTrue(tm.collidesWith(tris2), "Multiple tiles & triangles");
    }
}
