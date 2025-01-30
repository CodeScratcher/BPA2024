package org.keefeteam.atlantis.ui;

import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;
import java.util.Set;

/**
 * An interface to represent menus that can be swapped between
 */
public interface Menu {

    /**
     * Initialize the menu
     * @param gameState A reference to game state
     */
    void initialize(GameState gameState);

    /**
     * Update the menu
     * @param inputEvents The events that's happened this turn
     */
    void update(Set<InputEvent> inputEvents);

    /**
     * Dispose of the menu
     */
    void dispose();
}
