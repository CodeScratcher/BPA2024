package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.*;
import org.keefeteam.atlantis.coordinates.WorldCoordinate;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
public class Player implements Entity, Renderable {
    @Getter
    @Setter
    private WorldCoordinate position = new WorldCoordinate(new Vector2(0, 0));

    @Setter
    @NonNull
    private Texture texture;

    public static final int PLAYER_SPEED = 300;

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
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.getCoord().x, position.getCoord().y);
    }
}
