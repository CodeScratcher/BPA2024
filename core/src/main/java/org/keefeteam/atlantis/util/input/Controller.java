package org.keefeteam.atlantis.util.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    /**
     * Converts key presses into input events
     * @return A list of input events
     */
    public List<InputEvent> getEvents() {
        List<InputEvent> events = new ArrayList<>();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            events.add(InputEvent.Left);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            events.add(InputEvent.Right);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            events.add(InputEvent.Up);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            events.add(InputEvent.Down);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            events.add(InputEvent.Interact);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            events.add(InputEvent.UIConfirm);
        }
        return events;
    }
}
