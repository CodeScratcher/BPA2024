package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
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

    private final int PLAYER_SPEED = 5;

    @Override
    public void update(GameState gameState, List<InputEvent> events) {
        Vector2 newPos = new Vector2(0, 0);
        for (InputEvent event : events) {
            switch (event) {
                case Up:
                    newPos.y += 5;
                    break;
                case Down:
                    newPos.y -= 5;
                    break;
                case Left:
                    newPos.x -= 5;
                    break;
                case Right:
                    newPos.x += 5;
                    break;
            }
        }

        position = position.add(newPos.nor().scl(PLAYER_SPEED));
    }

    @Override
    public void render() {

    }
}
