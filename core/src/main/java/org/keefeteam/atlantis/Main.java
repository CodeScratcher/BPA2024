package org.keefeteam.atlantis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.keefeteam.atlantis.ui.ChoiceMenu;
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
    private boolean earlDone;
    private InteractZone interactZone4;
    private InteractZone interactZone5;
    private InteractZone interactZone7;
    private InteractZone interactZone8;
    private InteractZone interactZone9;
    @Override
    public void create() {



        batch = new SpriteBatch();
        sr = new ShapeRenderer();

        image = new Texture("libgdx.png");
        Texture img2 = new Texture("blackplaceholder.png");
        controller = new Controller();


        player = new Player(img2);
        entities = new ArrayList<>();



        WorldCoordinate playerPosition = player.getPosition();
        theCamera = new Camera(playerPosition);
        entities.add(theCamera);

        Vector2 p1 = new Vector2(1, 1);
        Vector2 p2 = new Vector2(p1.x + TILE_SIZE - 2, p1.y);
        Vector2 p3 = new Vector2(p1.x, p1.y + TILE_SIZE - 2);
        Vector2 p4 = new Vector2(p2.x, p3.y);

        List<Triangle> tris = new ArrayList<>();
        tris.add(new Triangle(p1, p2, p3));
        tris.add(new Triangle(p2, p3, p4));

        handler = new TiledTilemapHandler();
        handler.initialize("tileset/test.tmx", batch, theCamera);
        tilemap = handler.createTilemap();
        entities.add(tilemap);
        entities.add(player);

        // Talk to earl
        InteractZone interactZone = new InteractZone(new TileCoordinate(17, 460/16), tris, (gameState, player) -> {
            DialogueMenu test = null;
            if(earlDone){
                test = new DialogueMenu("Just get me what I need mate.");
            }
            else{
                test = new DialogueMenu("Hey mate. The name's Earl. There are never usually outsiders down here." +
                    "Look im very busy, but I cannot cross these spikes and finish research without armor. Please take this " +
                    "cloth and use it to make me some armor there mate.");
                player.addItem(new Item("Cloth"));
                earlDone = true;
            }

            gameState.setMenu(test);
        });

        // Enter Atlantis
        InteractZone interactZone2 = new InteractZone(new TileCoordinate(44, 220/16), tris, (gameState, player) -> {
            //InputMenu inputMenu = new InputMenu((str, state) -> System.out.println(str));
            InputMenu inputMenu = new InputMenu("Atlantis", (state) -> {
                handler.disableDoor(tilemap, 0);
            }, (state) -> gameState.setMenu(new DialogueMenu("Incorrect!")), "Confirm");

            gameState.setMenu(inputMenu);
        });
        // Pull lever in maze
        InteractZone interactZone3 = new InteractZone(new TileCoordinate(75, 500/16), tris, (gameState, player) -> {
            //InputMenu inputMenu = new InputMenu((str, state) -> System.out.println(str));
            List<String> text = new ArrayList<>();
            text.add("Pull Lever");
            ChoiceMenu inputMenu = new ChoiceMenu(text, (Test, state) -> {
                handler.disableDoor(tilemap, 1);
            });
            gameState.setMenu(inputMenu);
        });

        // Hammer
        interactZone4 = new InteractZone(new TileCoordinate(82, 520/16), tris, (gameState, player) -> {
            //InputMenu inputMenu = new InputMenu((str, state) -> System.out.println(str));
            List<String> text = new ArrayList<>();
            text.add("Grab Hammer");
            ChoiceMenu inputMenu = new ChoiceMenu(text, (Test, state) -> {
                handler.disableDoor(tilemap, 3);
                entities.remove(interactZone4);
                player.addItem(new Item("Hammer"));
            });

            gameState.setMenu(inputMenu);
        });
        //Break wall
        interactZone5 = new InteractZone(new TileCoordinate(3, 420/16), tris, (gameState, player) -> {
            List<Item> tempItems = player.getInventory();
            ChoiceMenu inputMenu = null;
            for(Item c : tempItems){
                if(c.getName().equals("Hammer")){
                    List<String> text = new ArrayList<>();
                    text.add("Use Hammer");
                    inputMenu = new ChoiceMenu(text, (Test, state) -> {
                        handler.disableDoor(tilemap, 2);
                        entities.remove(interactZone5);
                        player.removeItem(new Item("Hammer"));
                    });
                    break;
                }
            }

            gameState.setMenu(inputMenu);
        });
        //first number
        InteractZone interactZone6 = new InteractZone(new TileCoordinate(1, 375/16), tris, (gameState, player) -> {
            DialogueMenu test = new DialogueMenu("The first number is the quantity of atoms in one molecule of " +
                "the liquid used to cool the lava");
            gameState.setMenu(test);
        });
        //key1
        interactZone7 = new InteractZone(new TileCoordinate(39, 220/16), tris, (gameState, player) -> {
            //InputMenu inputMenu = new InputMenu((str, state) -> System.out.println(str));
            List<String> text = new ArrayList<>();
            text.add("Grab Key Fragment");
            ChoiceMenu inputMenu = new ChoiceMenu(text, (Test, state) -> {
                handler.disableDoor(tilemap, 4);
                entities.remove(interactZone7);
                player.addItem(new Item("Key Half One"));
            });

            gameState.setMenu(inputMenu);
        });
        //key2
        interactZone8 = new InteractZone(new TileCoordinate(50, 255/16), tris, (gameState, player) -> {
            //InputMenu inputMenu = new InputMenu((str, state) -> System.out.println(str));
            List<String> text = new ArrayList<>();
            text.add("Grab Key Fragment");
            ChoiceMenu inputMenu = new ChoiceMenu(text, (Test, state) -> {
                handler.disableDoor(tilemap, 5);
                entities.remove(interactZone8);
                player.addItem(new Item("Key Half Two"));
            });

            gameState.setMenu(inputMenu);
        });
        interactZone9 = new InteractZone(new TileCoordinate(44, 290/16), tris, (gameState, player) -> {
            List<Item> tempItems = player.getInventory();
            ChoiceMenu inputMenu = null;
            for(Item c : tempItems){
                if(c.getName().equals("Full Key")){
                    List<String> text = new ArrayList<>();
                    text.add("Use Key");
                    inputMenu = new ChoiceMenu(text, (Test, state) -> {
                        handler.disableDoor(tilemap, 6);
                        entities.remove(interactZone5);
                        player.removeItem(new Item("Full Key"));
                    });
                    break;
                }
            }

            gameState.setMenu(inputMenu);
        });
        entities.add(interactZone);
        entities.add(interactZone2);
        entities.add(interactZone3);
        entities.add(interactZone4);
        entities.add(interactZone5);
        entities.add(interactZone6);
        entities.add(interactZone7);
        entities.add(interactZone8);
        entities.add(interactZone9);
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
