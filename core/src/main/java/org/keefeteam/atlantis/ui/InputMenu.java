package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A menu where the player inputs an answer
 */
public class InputMenu implements Menu {
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    private Table menu;
    private String buttonText;
    private BiConsumer<String, GameState> onEntry;

    /**
     * Creates an input menu
     * @param onEntry A function that activates upon the menu's submittal, takes a string representing the input and the gamestate
     */
    public InputMenu(BiConsumer<String, GameState> onEntry) {
        this.onEntry = onEntry;
    }

    /**
     * Creates an input menu that has a correct answer
     * @param isCorrect The correct answer
     * @param correct Function to call upon correct answer
     * @param incorrect Function to call upon incorrect answer
     * @param bText Button text
     */
    public InputMenu(String isCorrect, Consumer<GameState> correct, Consumer<GameState> incorrect, String bText) {
        this((str, state) -> {
            if (isCorrect.equalsIgnoreCase(str)){
                correct.accept(state);
            }
            else incorrect.accept(state);
        });
        this.buttonText = bText;
    }

    @Override
    public void initialize(GameState gameState) {
        this.gameState = gameState;
        gameState.setPaused(true);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));

        menu = new Table();
        stage.addActor(menu);

        TextField textField = new TextField("", skin);
        textField.setWidth(500);

        TextButton textButton = new TextButton(buttonText, skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gameState.setMenu(null);
                gameState.setPaused(false);
                onEntry.accept(textField.getText(), gameState);

            }
        });

        menu.add(textField).width(500).row();
        menu.add(textButton);
        menu.setFillParent(true);


    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
