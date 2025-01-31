package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.*;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.ui.InventoryMenu;
import org.keefeteam.atlantis.util.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.collision.Collider;
import org.keefeteam.atlantis.util.input.InputEvent;
import org.keefeteam.atlantis.util.Item;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static org.keefeteam.atlantis.util.coordinates.TileCoordinate.TILE_SIZE;

@RequiredArgsConstructor
@AllArgsConstructor
/**
 * The character controlled by the player.
 */
public class Player implements Entity, Renderable {
    /**
     * The position of the player
     */
    @Getter
    @Setter
    private WorldCoordinate position = new WorldCoordinate(new Vector2(704, 20));

    /**
     * The texture of the player
     */
    @Setter
    @NonNull
    private Texture texture;
    Texture playerForward = new Texture("..\\assets\\sprites\\sp_forward_player.png");
    Texture playerRight = new Texture("..\\assets\\sprites\\sp_right_player.png");
    Texture playerLeft = new Texture("..\\assets\\sprites\\sp_left_player.png");
    Texture playerBack = new Texture("..\\assets\\sprites\\sp_back_player.png");

    private double iframes = 0;
    private int hp = 100;


    /**
     * The speed of the player
     */
    public static final int PLAYER_SPEED = 100;
    /**
     * The size of the increments by which the player steps in case of collision
     */
    public static final float REPAIR_SPEED = 1f;

    /**
     * The inventory of the player
     */
    @Getter
    @Setter
    private List<Item> inventory = new ArrayList<>();

    /**
     * Gets the hitbox of the player based on their position
     * @return The hitbox of the player
     */
    public List<Triangle> getTris() {
        return getTris(position.getCoord());
    }

    /**
     * Gets the hitbox of the player
     * @param p1 Where the hitbox is located, relative to world coordinates
     * @return The hitbox, shifted to that location
     */
    public List<Triangle> getTris(Vector2 p1) {
        List<Triangle> tris = new ArrayList<Triangle>();
        Vector2 alteredP1 = new Vector2(1, 1).add(p1);
        Vector2 p2 = new Vector2(p1.x + 15, p1.y);
        Vector2 p3 = new Vector2(p1.x, p1.y + 15);
        Vector2 p4 = new Vector2(p2.x, p3.y);
        tris.add(new Triangle(alteredP1, p2, p3));
        tris.add(new Triangle(p2, p3, p4));
        return tris;
    }

    //public WorldCoordinate getPosition() { return position; }


    @Override
    public void update(GameState gameState, Set<InputEvent> events) {

        Vector2 posChange = move(events);
        position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(posChange.nor().scl(PLAYER_SPEED * gameState.getDelta())));

        System.out.println(position.getCoord().x/16 + " ; " + position.getCoord().y/16);

        handleCollision(gameState, posChange, events);

        iframes -= gameState.getDelta();

        if (events.contains(InputEvent.Inventory) && gameState.getMenu() == null) {
            gameState.setMenu(new InventoryMenu(this));
        }

    }

    private void handleCollision(GameState gameState, Vector2 posChange, Set<InputEvent> events) {
        boolean colliding = false;

        boolean isInteracting = events.contains(InputEvent.Interact);

        for (Entity entity : gameState.getEntities()) {
            if (entity instanceof Collider collider) {
                if (collider.getColliderTypes().contains(Collider.ColliderTypes.WALL) && collider.collidesWith(getTris()) && !posChange.equals(new Vector2(0, 0))) {
                    Vector2 change = new Vector2(posChange).nor().scl(PLAYER_SPEED * gameState.getDelta());
                    position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(new Vector2(posChange).nor().scl(-PLAYER_SPEED * gameState.getDelta())));

                    double actualSpeed = PLAYER_SPEED * gameState.getDelta();

                    double amount = 0.0f;

                    while (Math.abs(amount) < Math.abs(change.x) && !collider.collidesWith(getTris())) {
                        amount += change.x * REPAIR_SPEED / actualSpeed;
                        position.getCoord().x += change.x * REPAIR_SPEED / actualSpeed;
                    }

                    if (collider.collidesWith(getTris())) {
                        position.getCoord().x -= change.x * REPAIR_SPEED / actualSpeed;
                    }

                    amount = 0.0f;

                    while (Math.abs(amount) < Math.abs(change.y) && !collider.collidesWith(getTris())) {
                        amount += change.y * REPAIR_SPEED / actualSpeed;
                        position.getCoord().y += change.y * REPAIR_SPEED / actualSpeed;
                    }

                    if (collider.collidesWith(getTris())) {
                        position.getCoord().y -= change.y * REPAIR_SPEED / actualSpeed;
                    }
                }

                if (isInteracting && collider.getColliderTypes().contains(Collider.ColliderTypes.INTERACTABLE) && collider.collidesWith(getTris())) {
                    InteractZone interactZone = (InteractZone)collider;

                    interactZone.getOnInteract().call(gameState, this);
                }
            }
        }
    }

    private Vector2 move(Set<InputEvent> events) {
        Vector2 posChange = new Vector2(0, 0);
        for (InputEvent event : events) {
            switch (event) {
                case Up:
                    posChange.y += 1;
                    texture = (playerBack);
                    break;
                case Down:
                    posChange.y -= 1;
                    texture = (playerForward);
                    break;
                case Left:
                    posChange.x -= 1;
                    texture = (playerLeft);
                    break;
                case Right:
                    posChange.x += 1;
                    texture = (playerRight);
                    break;
            }
        }

        return posChange;
    }

    /**
     * Check if inventory contains item
     * @param itemName The item to check for
     * @return Whether the inventory contains the item
     */
    public boolean checkInventory(String itemName) {
        boolean ret = false;
        for (Item i : inventory) {
            if (i.getName().equals(itemName)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.getCoord().x, position.getCoord().y, 16, 16);
    }

    /**
     * Add an item to the inventory
     * @param a The item to add
     */
    public void addItem(Item a) {
        this.inventory.add(a);
    }

    /**
     * Remove an item from the inventory
     * @param a The idem to remove
     */
    public void removeItem(Item a) {
        this.inventory.remove(a);
    }
}
