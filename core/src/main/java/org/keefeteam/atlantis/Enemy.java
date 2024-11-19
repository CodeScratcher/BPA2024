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
public class Enemy implements Entity, Renderable {
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
    public void update(GameState gameState, List<InputEvent> events) {
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
                    colliding = true;
                    float repairX = 0;
                    float repairY = 0;

                    while (collider.collidesWith(getTris(new Vector2(position.getCoord().x + repairX, position.getCoord().y)))) {
                        repairX -= REPAIR_SPEED * (posChange.x == 0 ? 10000000 : (posChange.x / Math.abs(posChange.x)));
                    }

                    while (collider.collidesWith(getTris(new Vector2(position.getCoord().x , position.getCoord().y + repairY)))) {
                        repairY -= REPAIR_SPEED * (posChange.y == 0 ? 10000000 : (posChange.y / Math.abs(posChange.y)));
                    }

                    if (Math.abs(repairX) > Math.abs(repairY)) {
                        position.getCoord().y += repairY;
                    }
                    else if (Math.abs(repairY) > Math.abs(repairX)) {
                        position.getCoord().x += repairX;
                    }
                    else {
                        position = WorldCoordinate.addWorldCoordinates(position, new WorldCoordinate(new Vector2(repairX, repairY)));
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
}
