package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.*;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;
import org.keefeteam.atlantis.util.Triangle;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
public class Player implements Entity, Renderable {
    @Getter
    @Setter
    private WorldCoordinate position = new WorldCoordinate(new Vector2(-1, -1));

    @Setter
    @NonNull
    private Texture texture;

    public static final int PLAYER_SPEED = 300;
    public static final float REPAIR_SPEED = 1f;

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
    public void update(GameState gameState, List<InputEvent> events) {
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

        position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(posChange.nor().scl(PLAYER_SPEED * gameState.getDelta())));

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

        if (!colliding) {
            System.out.println("Not colliding");
        }


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.getCoord().x, position.getCoord().y);
    }


    public float getWorldX(){
        return this.position.getCoord().x;
    }
    public float getWorldY(){
        return this.position.getCoord().y;
    }
}
