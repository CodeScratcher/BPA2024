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

public class ImageMenu implements Menu {
    // The text that is being displayed
    private Texture image;
    private Stage stage;
    private Table table;
    private Skin skin;
    private GameState gameState;

    //Where the text is printed

    public ImageMenu(Texture i){
        this.image = i;
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
