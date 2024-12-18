package org.keefeteam.atlantis.ui;

import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;
import java.util.Set;

public interface Menu {

    public void initialize(GameState gameState);
    public void update(Set<InputEvent> inputEvents);
    public void dispose();
}
