package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GameState {
    @NonNull private List<Entity> entities;
    private float delta = 0.0f;

    public void update(List<InputEvent> events) {
        delta = Gdx.graphics.getDeltaTime();

        for (Entity entity : entities) {
            entity.update(this, events);
        }
    }
}
