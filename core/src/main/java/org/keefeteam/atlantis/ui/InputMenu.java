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

public class InputMenu implements Menu {
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    private Table menu;
    private BiConsumer<String, GameState> onEntry;

    public InputMenu(BiConsumer<String, GameState> onEntry) {
        this.onEntry = onEntry;
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

        TextButton textButton = new TextButton("Confirm", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                onEntry.accept(textField.getText(), gameState);
                gameState.setMenu(null);
                gameState.setPaused(false);
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
