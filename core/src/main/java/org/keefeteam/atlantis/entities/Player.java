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

@RequiredArgsConstructor
@AllArgsConstructor
public class Player implements Entity, Renderable {
    @Getter
    @Setter
    private WorldCoordinate position = new WorldCoordinate(new Vector2(-1, -1));

    @Setter
    @NonNull
    private Texture texture;

    private double iframes = 0;
    private int hp = 100;

    public static final int PLAYER_SPEED = 300;
    public static final float REPAIR_SPEED = 1f;

    @Getter
    @Setter
    public List<Item> inventory = new ArrayList<>();

    public List<Triangle> getTris() {
        return getTris(position.getCoord());
    }

    public List<Triangle> getTris(Vector2 p1) {
        List<Triangle> tris = new ArrayList<Triangle>();
        Vector2 p2 = new Vector2(p1.x + 64, p1.y);
        Vector2 p3 = new Vector2(p1.x, p1.y + 64);
        Vector2 p4 = new Vector2(p2.x, p3.y);
        tris.add(new Triangle(p1, p2, p3));
        tris.add(new Triangle(p2, p3, p4));
        return tris;
    }

    //public WorldCoordinate getPosition() { return position; }


    @Override
    public void update(GameState gameState, Set<InputEvent> events) {
        Vector2 posChange = move(events);
        position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(posChange.nor().scl(PLAYER_SPEED * gameState.getDelta())));


        handleCollision(gameState, posChange, events);

        System.out.println("HP: " + hp);

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

                    double amount = 0.0f;

                    while (Math.abs(amount) < Math.abs(change.x) && !collider.collidesWith(getTris())) {
                        amount += change.x * REPAIR_SPEED / PLAYER_SPEED;
                        position.getCoord().x += change.x * REPAIR_SPEED / PLAYER_SPEED;
                    }

                    if (collider.collidesWith(getTris())) {
                        position.getCoord().x -= change.x * REPAIR_SPEED / PLAYER_SPEED;
                    }

                    amount = 0.0f;

                    while (Math.abs(amount) < Math.abs(change.y) && !collider.collidesWith(getTris())) {
                        amount += change.y * REPAIR_SPEED / PLAYER_SPEED;
                        position.getCoord().y += change.y * REPAIR_SPEED / PLAYER_SPEED;
                    }

                    if (collider.collidesWith(getTris())) {
                        position.getCoord().y -= change.y * REPAIR_SPEED / PLAYER_SPEED;
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
                    break;
                case Down:
                    posChange.y -= 1;
                    break;
                case Left:
                    posChange.x -= 1;
                    break;
                case Right:
                    posChange.x += 1;
                    break;
            }
        }


        return posChange;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.getCoord().x, position.getCoord().y);
    }
}
