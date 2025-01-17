package org.keefeteam.atlantis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.keefeteam.atlantis.ui.InputMenu;
import org.keefeteam.atlantis.util.Item;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import org.keefeteam.atlantis.util.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.entities.*;
import org.keefeteam.atlantis.ui.DialogueMenu;
import org.keefeteam.atlantis.util.coordinates.Camera;
import org.keefeteam.atlantis.util.input.Controller;
import org.keefeteam.atlantis.util.input.InputEvent;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.*;
import org.keefeteam.atlantis.util.TiledTilemapHandler;
import static org.keefeteam.atlantis.util.coordinates.TileCoordinate.TILE_SIZE;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private GameState gameState;
    private ArrayList<Entity> entities;
    private Player player; // TODO REMOVE AND REPLACE LOGIC FOR RENDERING, JUST FOR TESTING
    private Controller controller;
    private ShapeRenderer sr;
    private Camera theCamera;
    private TiledTilemapHandler handler;
    private Tilemap tilemap;

    @Override
    public void create() {



        batch = new SpriteBatch();
        sr = new ShapeRenderer();

        image = new Texture("libgdx.png");
        Texture img2 = new Texture("blackplaceholder.png");
        controller = new Controller();


        player = new Player(img2);
        Item temp1 = new Item("Key Half One");
        Item temp2 = new Item("Key Half Two");
        player.addItem(temp1);
        player.addItem(temp2);
        entities = new ArrayList<>();

        /*
        Enemy enemy = new Enemy(new WorldCoordinate(new Vector2(300,  300)), img2, null);
        entities.add(enemy);
        */


        WorldCoordinate playerPosition = player.getPosition();
        theCamera = new Camera(playerPosition);
        entities.add(theCamera);

        Vector2 p1 = new Vector2(0, 0);
        Vector2 p2 = new Vector2(p1.x + TILE_SIZE, p1.y);
        Vector2 p3 = new Vector2(p1.x, p1.y + TILE_SIZE);
        Vector2 p4 = new Vector2(p2.x, p3.y);

        List<Triangle> tris = new ArrayList<>();
        tris.add(new Triangle(p1, p2, p3));
        tris.add(new Triangle(p2, p3, p4));

        handler = new TiledTilemapHandler();
        handler.initialize("tileset/test.tmx", batch, theCamera);
        tilemap = handler.createTilemap();
        entities.add(tilemap);
        entities.add(player);

        InteractZone interactZone = new InteractZone(new TileCoordinate(2, 2), tris, (gameState, player) -> {
            DialogueMenu test = new DialogueMenu("Lorem ipsum dolor sit amet");
            gameState.setMenu(test);
        });

        InteractZone interactZone2 = new InteractZone(new TileCoordinate(13, 14), tris, (gameState, player) -> {
            //InputMenu inputMenu = new InputMenu((str, state) -> System.out.println(str));
            InputMenu inputMenu = new InputMenu("Atlantis", (state) -> {
                handler.disableDoor(tilemap, 0);
            }, (state) -> gameState.setMenu(new DialogueMenu("Incorrect!")));

            gameState.setMenu(inputMenu);
        });

        entities.add(interactZone);
        entities.add(interactZone2);

        gameState = new GameState(entities);

    }

    @Override
    public void render() {
        // Main loop goes here
        // https://gameprogrammingpatterns.com
        // https://gameprogrammingpatterns.com/game-loop.html
        // https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller
        // Render happens once per frame, so thank god we don't have to handle the full loop, just the internals
        Set<InputEvent> eventList = controller.getEvents();

        if (!gameState.isPaused()) gameState.update(eventList);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.setProjectionMatrix(theCamera.getCamera().combined);

        batch.begin();
        gameState.render(batch);
        batch.end();

        if (gameState.getMenu() != null) {
            gameState.getMenu().update(eventList);
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
