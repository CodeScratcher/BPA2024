package org.keefeteam.atlantis.ui;

import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;

public interface Menu {

    public void initialize(GameState gameState);
    public void update(List<InputEvent> inputEvents);
    public void dispose();
}
