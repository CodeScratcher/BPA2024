/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.keefeteam.atlantis.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.entities.Entity;
import org.keefeteam.atlantis.entities.Renderable;
import org.keefeteam.atlantis.entities.Tile;
import org.keefeteam.atlantis.entities.Tilemap;
import org.keefeteam.atlantis.util.collision.Triangle;
import org.keefeteam.atlantis.util.coordinates.Camera;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import static org.keefeteam.atlantis.util.coordinates.TileCoordinate.TILE_SIZE;
import org.keefeteam.atlantis.util.input.InputEvent;

/**
 *
 * @author afisher
 */

@Getter
@Setter
public class TiledTilemapHandler implements Renderable {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    Camera camera;
    
    public void initialize(String url, SpriteBatch batch, Camera camera) {
        map = new TmxMapLoader().load(url);
        float unitScale = 1f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale, batch);
        this.camera = camera;
    }
    
    public Tilemap createTilemap() {
        Vector2 p1 = new Vector2(0, 0);
        Vector2 p2 = new Vector2(p1.x + TILE_SIZE, p1.y);
        Vector2 p3 = new Vector2(p1.x, p1.y + TILE_SIZE);
        Vector2 p4 = new Vector2(p2.x, p3.y);

        List<Triangle> tris = new ArrayList<>();
        tris.add(new Triangle(p1, p2, p3));
        tris.add(new Triangle(p2, p3, p4));
    
        Map<TileCoordinate, Tile> tiles = new HashMap<>();
        
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
        
        for (int i = 0; i < layer.getWidth(); i++) {
            for (int j = 0; j < layer.getHeight(); j++) {
                if (layer.getCell(i, j) != null) {
                    tiles.put(new TileCoordinate(i, j), new Tile(tris));
                }
            }
        }
        
        Tilemap tm = new Tilemap(tiles, this);
        
        return tm;
    }

    @Override
    public void render(SpriteBatch batch) {
        renderer.setView(camera.getCamera());
        renderer.renderTileLayer((TiledMapTileLayer)map.getLayers().get(0));
        renderer.renderTileLayer((TiledMapTileLayer)map.getLayers().get(1));
    }
}
