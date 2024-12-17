package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private Menu menu;
    private float delta = 0.0f;
    private boolean paused = false;

    public void update(List<InputEvent> events) {
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
