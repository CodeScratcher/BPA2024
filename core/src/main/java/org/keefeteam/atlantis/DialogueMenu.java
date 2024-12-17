package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.*;

public class DialogueMenu implements Menu {
    private String text;
    private int maxLength;
    private int textIndexOne;
    private int textIndexTwo;
    private boolean textFinished;
    boolean inputControl;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Label textLabel;
    private GameState gameState;

    public DialogueMenu(String t){
        this.text = t;
    }

    @Override
    public void initialize(GameState g) {
        maxLength = 5;
        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();

        BitmapFont font = new BitmapFont();
        Skin tempSkin = new Skin();
        tempSkin.add("default-font", font);
        tempSkin.add("default", new Label.LabelStyle(font, Color.WHITE));
        skin = tempSkin;
        //skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        String firstOutput = "";
        if(text.length() > maxLength){
            firstOutput =  text.substring(0, maxLength);
            this.textFinished = false;
        }
        else{
            firstOutput = text;
            this.textFinished = true;
        }

        textLabel = new Label(firstOutput, skin);

        table.add(textLabel);
        stage.addActor(table);
    }

    @Override
    public void update(List<InputEvent> inputEvents) {
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            System.out.println(this.text);
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if(inputEvents.contains(InputEvent.UIConfirm)){
            if(!textFinished){
                textIndexOne += maxLength;
                textIndexTwo = textIndexOne + maxLength;
                if(textIndexTwo > text.length()){
                    textIndexTwo = text.length();
                    textFinished = true;
                }
                String newValue = text.substring(textIndexOne, textIndexTwo);

                stage.getActors().removeValue(table, true);

                textLabel = new Label(newValue, skin);
                table = new Table();
                table.add(textLabel);
                stage.addActor(table);
            }
            else{
                //Destroy label
                stage.getActors().removeValue(table, true);
                this.gameState.setPaused(false);
            }
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
