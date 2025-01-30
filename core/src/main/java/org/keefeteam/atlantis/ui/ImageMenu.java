package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.*;
import java.util.function.Supplier;

/**
 * A menu that displays an image
 */
public class ImageMenu implements Menu {
    // The text that is being displayed
    private Texture image;
    private Stage stage;
    private Table table;
    private Skin skin;
    private GameState gameState;
    private Supplier<Boolean> onEnd;

    //Where the text is printed

    /**
     * Create an image menu
     * @param i The image displayed
     */
    public ImageMenu(Texture i){
        this.image = i;
        onEnd = () -> false;
    }

    /**
     * Create an image menu that does something upon completion
     * @param i The image menu
     * @param onEnd The function called upon completion
     */
    public ImageMenu(Texture i, Supplier<Boolean> onEnd) {
        this.image = i;
        this.onEnd = onEnd;
    }


    @Override
    public void initialize(GameState g) {

        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        table.add(new Image(image));
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if(inputEvents.contains(InputEvent.UIConfirm)){
            if (onEnd.get()) return;
            table.remove();
            gameState.setMenu(null);
            this.gameState.setPaused(false);
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
