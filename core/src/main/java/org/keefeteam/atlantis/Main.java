package org.keefeteam.atlantis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.keefeteam.atlantis.coordinates.TileCoordinate;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.Triangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private GameState gameState;
    private ArrayList<Entity> entities;
    private Player player; // TODO REMOVE AND REPLACE LOGIC FOR RENDERING, JUST FOR TESTING
    private Controller controller;
    private ShapeRenderer sr;
    private Tile testTile;
    private Camera theCamera;


    @Override
    public void create() {
        batch = new SpriteBatch();
        sr = new ShapeRenderer();

        image = new Texture("libgdx.png");
        Texture img2 = new Texture("blackplaceholder.png");
        controller = new Controller();


        player = new Player(img2);

        entities = new ArrayList<>();
        entities.add(player);

        /*
        Enemy enemy = new Enemy(new WorldCoordinate(new Vector2(300,  300)), img2, null);
        entities.add(enemy);
        */

        WorldCoordinate playerPosition = player.getPosition();
        theCamera = new Camera(playerPosition);
        entities.add(theCamera);

        Vector2 p1 = new Vector2(0, 0);
        Vector2 p2 = new Vector2(p1.x + 64, p1.y);
        Vector2 p3 = new Vector2(p1.x, p1.y + 64);
        Vector2 p4 = new Vector2(p2.x, p3.y);

        List<Triangle> tris = new ArrayList<>();
        tris.add(new Triangle(p1, p2, p3));
        tris.add(new Triangle(p2, p3, p4));

        testTile = new Tile(tris, img2);

        Map<TileCoordinate, Tile> tiles = new HashMap<>();
        tiles.put(new TileCoordinate(1, 1), testTile);
        tiles.put(new TileCoordinate(1, 2), testTile);
        tiles.put(new TileCoordinate(2, 1), testTile);

        Tilemap tilemap = new Tilemap(tiles);

        entities.add(tilemap);


        gameState = new GameState(entities);

    }

    @Override
    public void render() {
        // Main loop goes here
        // https://gameprogrammingpatterns.com
        // https://gameprogrammingpatterns.com/game-loop.html
        // https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller
        // Render happens once per frame, so thank god we don't have to handle the full loop, just the internals
        List<InputEvent> eventList = controller.getEvents();
        gameState.update(eventList);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.setProjectionMatrix(theCamera.getCamera().combined);
        batch.begin();
        gameState.render(batch);
        batch.end();


    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
