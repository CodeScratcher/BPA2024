package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.List;

public class DialogueEntity implements Entity {
    private String text;
    @Override
    public void update(GameState gameState, List<InputEvent> events) {
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            System.out.println("E IS BEING PUSHED AT THE CURRENT MOMENT AND I'M NOT EXTENDING THIS LINE TO MAKE IT EASIER TO SEE");
        }
    }
}
