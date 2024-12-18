package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.keefeteam.atlantis.entities.Entity;
import org.keefeteam.atlantis.entities.Renderable;
import org.keefeteam.atlantis.ui.Menu;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class GameState {
    @NonNull private List<Entity> entities;
    private Menu menu;
    private float delta = 0.0f;
    private boolean paused = false;

    public void update(Set<InputEvent> events) {
        delta = Gdx.graphics.getDeltaTime();

        for (Entity entity : entities) {
            entity.update(this, events);
        }
    }

    public void render(SpriteBatch batch) {
        for (Entity entity : entities) {
            if (entity instanceof Renderable renderable) {
                renderable.render(batch);
            }
        }
    }

    public void setMenu(Menu menu) {
        if (this.menu != null) this.menu.dispose();
        if (menu != null) menu.initialize(this);
        this.menu = menu;
    }
}
