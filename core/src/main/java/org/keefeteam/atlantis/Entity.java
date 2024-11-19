package org.keefeteam.atlantis;
import java.util.List;

import com.badlogic.gdx.Game;

/**
 * An interface defining something which updates over time
 */
public interface Entity {
    /**
     * Update an entity based on time, game state, and input events
     * @param gameState The current state of the game
     * @param events Input events
     */
    void update(GameState gameState, List<InputEvent> events);
}
