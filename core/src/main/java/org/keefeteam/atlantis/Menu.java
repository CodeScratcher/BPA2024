package org.keefeteam.atlantis;

import java.util.List;

public interface Menu {

    public void display();
    public void update(List<InputEvent> inputEvents);
    public void dispose();
}
