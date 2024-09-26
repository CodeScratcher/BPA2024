package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GameState {
    private List<Entity> entities;
    private float delta;

    public void update(List<InputEvent> events) {
        delta = Gdx.graphics.getDeltaTime();
        for (Entity entity : entities) {
            entity.update(this, events);
        }
    }
}
