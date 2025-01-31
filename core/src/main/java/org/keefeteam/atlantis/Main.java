package org.keefeteam.atlantis;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.LittleEndianInputStream;
import com.badlogic.gdx.utils.ScreenUtils;
import org.keefeteam.atlantis.ui.*;
import org.keefeteam.atlantis.util.Item;
import org.keefeteam.atlantis.util.coordinates.TileCoordinate;
import org.keefeteam.atlantis.util.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.entities.*;
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
    private boolean machDone;
    private boolean hunterDone;
    private boolean oracleDone;
    private boolean earlComplete;
    private boolean machComplete;
    private boolean[] dones;
    private boolean hasFish;
    private boolean earlHelped;
    private DialogueMenu end;
    private InteractZone interactZone3;
    private InteractZone interactZone4;
    private InteractZone interactZone5;
    private InteractZone interactZone7;
    private InteractZone interactZone8;
    private InteractZone interactZone9;
    private InteractZone interactZone13;
    private InteractZone interactZone14;
    private InteractZone interactZone15;
    private InteractZone interactZone18;
    private InteractZone interactZone20;
    @Override
    public void create() {



        batch = new SpriteBatch();
        sr = new ShapeRenderer();

        image = new Texture("libgdx.png");
        Texture img2 = new Texture("..\\assets\\sprites\\sp_forward_player.png");

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


        dones = new boolean[3];
        // Talk to earl
        InteractZone interactZone = new InteractZone(new TileCoordinate(17, 460/16), tris, (gameState, player) -> {
            DialogueMenu test = null;
            if(!earlHelped){
                if(earlDone){
                    if(player.checkInventory("Scale Armor")){
                        player.removeItem(new Item("Scale Armor"));
                        test = new DialogueMenu("Excellent there mate, I can now conduct needed research");
                        earlHelped = true;
                    }
                    else{
                        test = new DialogueMenu("Just get me what I need mate.");
                    }
                }
                else{
                    test = new DialogueMenu("Hey mate. The name's Earl. There are never usually outsiders down here." +
                        "Look im very busy, but I cannot cross these spikes and finish research without armor. Please take this " +
                        "cloth and use it to make me some armor there mate.");
                    player.addItem(new Item("Cloth"));
                    earlDone = true;
                }
            }
            else{
                test = new DialogueMenu("Carry on now mate.");
            }

            gameState.setMenu(test);
        });

        // Enter Atlantis
        InteractZone interactZone2 = new InteractZone(new TileCoordinate(44, 220/16), tris, (gameState, player) -> {

            InputMenu inputMenu = new InputMenu("Atlantis", (state) -> {

                handler.disableDoor(tilemap, 0);
                gameState.setMenu(new DialogueMenu("(You feel a strong urge to check your pockets with Q)"));
            }, (state) -> gameState.setMenu(new DialogueMenu("Incorrect!")), "Confirm");
            DialogueMenu dialogueMenu = new DialogueMenu("Speak the name of this place to enter.", () -> {
                gameState.setMenu(inputMenu);
                return true;
            });
            gameState.setMenu(dialogueMenu);
        });
        // Pull lever in maze
        interactZone3 = new InteractZone(new TileCoordinate(75, 500/16), tris, (gameState, player) -> {

            List<String> text = new ArrayList<>();
            text.add("Pull Lever");
            ChoiceMenu inputMenu = new ChoiceMenu(text, (Test, state) -> {
                handler.disableDoor(tilemap, 1);
                entities.remove(interactZone3);
            });
            gameState.setMenu(inputMenu);
        });
        // Hammer
        interactZone4 = new InteractZone(new TileCoordinate(82, 520/16), tris, (gameState, player) -> {

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
            dones[0] = true;
            DialogueMenu test = new DialogueMenu("The first number is the quantity of atoms in one molecule of " +
                "the liquid used to cool the lava");
            gameState.setMenu(test);
        });
        //key1
        interactZone7 = new InteractZone(new TileCoordinate(39, 220/16), tris, (gameState, player) -> {

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

            List<String> text = new ArrayList<>();
            text.add("Grab Key Fragment");
            ChoiceMenu inputMenu = new ChoiceMenu(text, (Test, state) -> {
                handler.disableDoor(tilemap, 5);
                entities.remove(interactZone8);
                player.addItem(new Item("Key Half Two"));
            });

            gameState.setMenu(inputMenu);
        });
        //use the key
        interactZone9 = new InteractZone(new TileCoordinate(44, 290/16), tris, (gameState, player) -> {
            List<Item> tempItems = player.getInventory();
            ChoiceMenu inputMenu = null;
            for(Item c : tempItems){
                if(c.getName().equals("Full Key")){
                    List<String> text = new ArrayList<>();
                    text.add("Use Key");
                    inputMenu = new ChoiceMenu(text, (Test, state) -> {
                        handler.disableDoor(tilemap, 6);
                        entities.remove(interactZone9);
                        player.removeItem(new Item("Full Key"));
                    });
                    break;
                }
            }

            gameState.setMenu(inputMenu);
        });
        //third number
        InteractZone interactZone10 = new InteractZone(new TileCoordinate(88, 550/16), tris, (gameState, player) -> {
            dones[2] = true;
            DialogueMenu test = new DialogueMenu("The third number is the total number of items you have picked up off the floor.");
            gameState.setMenu(test);
        });
        //second number
        InteractZone interactZone11 = new InteractZone(new TileCoordinate(44, 520/16), tris, (gameState, player) -> {
            dones[1] = true;
            DialogueMenu test = new DialogueMenu("The second number is the additive quantity of the other two numbers divided by the first number, then subtract two");
            gameState.setMenu(test);
        });
        //open next door
        InteractZone interactZone12 = new InteractZone(new TileCoordinate(44, 580/16), tris, (gameState, player) -> {

            InputMenu inputMenu = new InputMenu("303", (state) -> {
                boolean exitIt = false;
                for (boolean done : dones) {
                    if (!done) {
                        exitIt = true;
                        break;
                    }
                }
                if(!exitIt){
                    handler.disableDoor(tilemap, 7);
                }

            }, (state) -> gameState.setMenu(new DialogueMenu("Incorrect!")), "Confirm");

            gameState.setMenu(inputMenu);
        });
        //circle
        interactZone13 = new InteractZone(new TileCoordinate(49, 42), tris, (gameState, player) -> {
            InputMenu inputMenu = new InputMenu("pi", (state) -> {
                player.addItem(new Item("Pie Shaped object"));
                handler.disableDoor(tilemap, 10);
                entities.remove(interactZone13);
            }, (state) -> gameState.setMenu(new DialogueMenu("Incorrect!")), "Confirm");

            DialogueMenu dialogueMenu = new DialogueMenu("He likes circles, what\'s his favourite snack", () -> {
                gameState.setMenu(inputMenu);

                return true;
            });
            if(hasFish){
                gameState.setMenu(dialogueMenu);
            }

        });
        //triangle
        interactZone14 = new InteractZone(new TileCoordinate(52, 42), tris, (gameState, player) -> {

            List<String> text = new ArrayList<>();
            text.add("Opposite");
            text.add("Cosine");
            text.add("Hypotenuse");
            text.add("Tangent");
            ChoiceMenu inputMenu = new ChoiceMenu(text, "Hypotenuse", (Test) -> {
                handler.disableDoor(tilemap, 9);
                entities.remove(interactZone14);
                player.addItem(new Item("Pythagoras Shaped object"));
            }, (Test) ->{
                System.out.println("WRONG");
            });
            DialogueMenu dialogueMenu = new DialogueMenu("Shortest distance between two points", () -> {
                gameState.setMenu(inputMenu);
                return true;
            });
            gameState.setMenu(dialogueMenu);
        });
        //rectangle
        interactZone15 = new InteractZone(new TileCoordinate(55, 42), tris, (gameState, player) -> {

            InputMenu inputMenu = new InputMenu("square", (state) -> {
                player.addItem(new Item("Black Rectangle"));
                handler.disableDoor(tilemap, 8);
                entities.remove(interactZone15);
            }, (state) -> gameState.setMenu(new DialogueMenu("Incorrect!")), "Confirm");
            DialogueMenu dialogueMenu = new DialogueMenu("Every square is a rectangle is a square but not every rectangle is a _____", () -> {
                gameState.setMenu(inputMenu);
                return true;
            });
            gameState.setMenu(dialogueMenu);
        });
        //Machine
        InteractZone interactZone16 = new InteractZone(new TileCoordinate(35, 41), tris, (gameState, player) -> {
            DialogueMenu test = null;
            if(machComplete){
                test = new DialogueMenu("Thanks, now we just sit back and watch, except I forgot what it was supposed to do");
            }
            else if(player.checkInventory("Pythagoras Shaped object") && machDone){
                test = new DialogueMenu("Hmmm, I see that triangle you go there. that might be too much voltage for" +
                    " the machine to handle, if only there was less voltage");
            }
            else if(player.checkInventory("Voltage Sign") && machDone){
                test = new DialogueMenu("Perfecto my friend. Take one of my scales... also have this thing I found on the ground");
                player.removeItem(new Item("Voltage Sign"));
                machComplete = true;
                player.addItem(new Item("Scale"));
                player.addItem(new Item("Queen Fragment"));
                handler.disableDoor(tilemap, 11);
            }
            else if(machDone){
                test = new DialogueMenu("I Just can't think of anything, please give me a solution!");
            }
            else{
                test = new DialogueMenu("Hey! You! Please help an Atlantean out! My machine doesn't work and I" +
                    " cannot figure out why! Everything I think of comes to reality. And NO I cannot just \"Think of a working machine\"" +
                    " because it has never worked before, so I have no basis. Look, can you just lend me a hand?");
                machDone = true;
            }

            gameState.setMenu(test);
        });
        //Hunter
        InteractZone interactZone17 = new InteractZone(new TileCoordinate(52, 54), tris, (gameState, player) -> {
            DialogueMenu test = null;
            if(!hunterDone){
                test = new DialogueMenu("Please... I need some food to live. Take my spear and get me some food.");
                player.addItem(new Item("Wooden Spear"));
                gameState.setMenu(test);
                hunterDone = true;
            }
            else if(player.checkInventory("Fishy Meat")){
                test = new DialogueMenu("What? Am I a troglodyte to you? At least put it on a plate with a fork.");
            }
            else if(player.checkInventory("Meal")){
                test = new DialogueMenu("Oh my, thank you so much. Please take this thing I found. Also, take the spear, its on the house.");
                player.removeItem(new Item("Meal"));
                player.addItem(new Item("Queen Fragment"));
            }
            else{
                test = new DialogueMenu("...");
            }
            gameState.setMenu(test);
        });
        //Get fish
        interactZone18 = new InteractZone(new TileCoordinate(73, 22), tris, (gameState, player) -> {
            List<Item> tempItems = player.getInventory();
            ChoiceMenu inputMenu = null;
            for(Item c : tempItems){
                if(c.getName().equals("Wooden Spear")){
                    List<String> text = new ArrayList<>();
                    text.add("Fish");
                    inputMenu = new ChoiceMenu(text, (Test, state) -> {
                        handler.disableDoor(tilemap, 12);
                        player.addItem(new Item("Fishy Meat"));
                        hasFish = true;
                        entities.remove(interactZone18);
                    });
                    break;
                }
            }

            gameState.setMenu(inputMenu);
        });
        //Oracle
        InteractZone interactZone19 = new InteractZone(new TileCoordinate(34, 52), tris, (gameState, player) -> {

            InputMenu qOne = new InputMenu("Earl", (state) -> {

                gameState.setMenu(new DialogueMenu("Correct, next question. What does he want.", () ->{
                    List<String> text = new ArrayList<>();
                    text.add("Research");
                    text.add("Money");
                    text.add("Fame");
                    text.add("Friends");
                    ChoiceMenu qTwo = new ChoiceMenu(text, "Research", (Test) -> {
                        List<String> nextText = new ArrayList<>();
                        nextText.add("Wooden Stick");
                        nextText.add("Scale Armor");
                        nextText.add("Fish");
                        nextText.add("Hammer");
                        ChoiceMenu qThree = new ChoiceMenu(nextText, "Scale Armor", (Test2) -> {
                            oracleDone = true;
                            gameState.setMenu(new DialogueMenu("Well Done, please check your pockets."));
                            player.addItem(new Item("Queen Fragment"));

                        }, (Test2) ->{
                            gameState.setMenu(new DialogueMenu("Wrong!"));
                        });
                        gameState.setMenu(qThree);
                    }, (Test) ->{
                        gameState.setMenu(new DialogueMenu("Wrong!"));
                    });
                    gameState.setMenu(qTwo);
                    return true;
                }));

            }, (state) -> gameState.setMenu(new DialogueMenu("WRONG!")), "Confirm");
            DialogueMenu dialogueMenu = new DialogueMenu("What is his name.", () -> {
                gameState.setMenu(qOne);
                return true;
            });
            gameState.setMenu(dialogueMenu);
        });
        //Place Chess Piece
        interactZone20 = new InteractZone(new TileCoordinate(43, 48), tris, (gameState, player) -> {
            List<Item> tempItems = player.getInventory();
            ImageMenu imageMenu = null;
            for(Item c : tempItems){
                if(c.getName().equals("Chess Queen")){
                    Texture texture = new Texture(Gdx.files.internal("chess.png"));
                    imageMenu = new ImageMenu(texture, () -> {
                        InputMenu inputMenu = new InputMenu("g5", state -> {
                            handler.disableDoor(tilemap, 13);
                            entities.remove(interactZone20);
                            player.removeItem(new Item("Chess Queen"));
                        }, state -> {},"Submit");
                        DialogueMenu dialogueMenu = new DialogueMenu("Where should the queen be to checkmate?", () -> {gameState.setMenu(inputMenu); return true;});
                        gameState.setMenu(dialogueMenu);
                        return true;
                    });
                    break;
                }
            }

            gameState.setMenu(imageMenu);
        });
        InteractZone interactZone21 = new InteractZone(new TileCoordinate( 43, 69), tris, (gameState, player) -> {
            ArrayList<String> choice = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                choice.add(String.valueOf(i));
            }
            MultipleMenu multi = new MultipleMenu(choice, (choices, state) -> {
                if (new HashSet<>(choices).containsAll(List.of("2", "5", "7", "8", "9", "11")) && Set.of("2", "5", "7", "8", "9", "11").containsAll(choices)) {
                    end = new DialogueMenu("As you input the shape, you hear a loud rumbling...", () -> {

                        entities.clear();
                        DialogueMenu t = null;
                        if(earlHelped){
                            //TODO write lore
                            t = new DialogueMenu("Earl runs into the control room and screams \"DON'T PRESS THAT BUTTON!\" Earl explains that after continuing his research, he found that the button would release a demonic entity to emerge, and that pressing the button would doom not only all of Atlantis, but the world.");
                        }
                        else{
                            t = new DialogueMenu("As you press the button, a dark viscous sludge emerges from the cracks of the ground, and starts to crack the ground until it shatters, as an eldritch demon rises out of the ground. You hear the panic of dozens of Atlanians as they attempt to escape to safety, but it is too late, as Atlantis crumbles around you.");
                        }
                        gameState.setMenu(t);
                        return true;
                    });
                    gameState.setMenu(end);
                }
            });
            DialogueMenu prompt = new DialogueMenu("What is the shape of this place?", () -> {
                gameState.setMenu(multi);
                return true;
            });
            gameState.setMenu(prompt);
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
        entities.add(interactZone10);
        entities.add(interactZone11);
        entities.add(interactZone12);
        entities.add(interactZone13);
        entities.add(interactZone14);
        entities.add(interactZone15);
        entities.add(interactZone16);
        entities.add(interactZone17);
        entities.add(interactZone18);
        entities.add(interactZone19);
        entities.add(interactZone20);
        entities.add(interactZone21);

        gameState = new GameState(entities);
        gameState.setMenu(new MainMenu());

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
