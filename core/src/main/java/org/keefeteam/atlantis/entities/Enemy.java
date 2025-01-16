package org.keefeteam.atlantis.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.*;
import org.keefeteam.atlantis.*;
import org.keefeteam.atlantis.util.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.collision.Collider;
import org.keefeteam.atlantis.util.input.InputEvent;
import org.keefeteam.atlantis.util.collision.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.keefeteam.atlantis.entities.Player.PLAYER_SPEED;

@RequiredArgsConstructor
@AllArgsConstructor
public class Enemy implements Entity, Renderable, Collider {
    @Getter
    @Setter
    private WorldCoordinate position = new WorldCoordinate(new Vector2(0, 0));

    @Setter
    @NonNull
    private Texture texture;

    private Player tracking;

    public static final int ENEMY_SPEED = 200;
    public static final float REPAIR_SPEED = 0.5f;

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

    @Override
    public void update(GameState gameState, Set<InputEvent> events) {
        Vector2 posChange = new Vector2(0, 0);

        if (tracking != null) {
            if (tracking.getPosition().getCoord().x < getPosition().getCoord().x) {
                posChange.x = -1;
            }
            else if (tracking.getPosition().getCoord().x > getPosition().getCoord().x) {
                posChange.x = 1;
            }

            if (tracking.getPosition().getCoord().y < getPosition().getCoord().y) {
                posChange.y = -1;
            }
            else if (tracking.getPosition().getCoord().y > getPosition().getCoord().y) {
                posChange.y = 1;
            }
        }
        else {
            for (Entity entity : gameState.getEntities()) {
                if (entity instanceof Player player && player.getPosition().getCoord().dst(position.getCoord()) < 200) {
                    tracking = player;
                    break;
                }
            }
        }

        position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(posChange.nor().scl(ENEMY_SPEED * gameState.getDelta())));

        boolean colliding = false;
        for (Entity entity : gameState.getEntities()) {
            if (entity instanceof Collider collider) {
                if (collider.getColliderTypes().contains(Collider.ColliderTypes.WALL) && collider.collidesWith(getTris()) && !posChange.equals(new Vector2(0, 0))) {
                    Vector2 change = new Vector2(posChange).nor().scl(PLAYER_SPEED * gameState.getDelta());
                    position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(new Vector2(posChange).nor().scl(-PLAYER_SPEED * gameState.getDelta())));

                    double amount = 0.0f;

                    while (!collider.collidesWith(getTris()) && Math.abs(amount) < Math.abs(change.x)) {
                        amount += change.x * REPAIR_SPEED / PLAYER_SPEED;
                        position.getCoord().x += change.x * REPAIR_SPEED / PLAYER_SPEED;
                    }

                    if (collider.collidesWith(getTris())) {
                        position.getCoord().x -= change.x * REPAIR_SPEED / PLAYER_SPEED;
                    }

                    amount = 0.0f;

                    while (!collider.collidesWith(getTris()) && Math.abs(amount) < Math.abs(change.y)) {
                        amount += change.y * REPAIR_SPEED / PLAYER_SPEED;
                        position.getCoord().y += change.y * REPAIR_SPEED / PLAYER_SPEED;
                    }

                    if (collider.collidesWith(getTris())) {
                        position.getCoord().y -= change.y * REPAIR_SPEED / PLAYER_SPEED;
                    }
                }
            }
        }


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.getCoord().x, position.getCoord().y);
    }

    @Override
    public boolean collidesWith(List<Triangle> tris) {
        for (Triangle tri : getTris()) {
            for (Triangle tri2 : tris) {
                if (tri.triangleOverlap(tri2)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Set<ColliderTypes> getColliderTypes() {
        return Set.of(ColliderTypes.ENEMY);
    }
}
