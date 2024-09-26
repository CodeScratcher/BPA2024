package org.keefeteam.atlantis;
import java.util.List;

import com.badlogic.gdx.Game;

public interface Entity {
    void update(GameState gameState, List<InputEvent> events);
}
