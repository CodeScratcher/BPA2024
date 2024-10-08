package org.keefeteam.atlantis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Wall implements Entity, Renderable{
    Vector2 position;
    Texture texture;


    @Override
    public void update(GameState gameState, List<InputEvent> events) {
        // Walls don't update
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}
