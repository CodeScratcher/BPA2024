package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
public class Player implements Entity, Renderable {
    @Getter
    @Setter
    private Vector2 position = new Vector2(0, 0);

    @Setter
    @NonNull
    private Texture texture;

    private final int PLAYER_SPEED = 300;

    @Override
    public void update(GameState gameState, List<InputEvent> events) {
        Vector2 newPos = new Vector2(0, 0);
        for (InputEvent event : events) {
            switch (event) {
                case Up:
                    newPos.y += 1;
                    break;
                case Down:
                    newPos.y -= 1;
                    break;
                case Left:
                    newPos.x -= 1;
                    break;
                case Right:
                    newPos.x += 1;
                    break;
            }
        }

        position = position.add(newPos.nor().scl(PLAYER_SPEED * gameState.getDelta()));
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}
