package org.keefeteam.atlantis.entities;
import java.util.List;

import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

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
