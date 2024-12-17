package org.keefeteam.atlantis;

import java.util.List;

public interface Menu {

    public void initialize(GameState gameState);
    public void update(List<InputEvent> inputEvents);
    public void dispose();
}
