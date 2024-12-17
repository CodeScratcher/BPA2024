package org.keefeteam.atlantis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.*;

public class DialogueMenu implements Menu {
    private String text;
    private final int maxLength = 5;
    private int textIndexOne;
    private int textIndexTwo;
    private boolean textFinished;
    boolean inputControl;
    private Stage stage;
    private Table table;
    private Skin skin;
    private GameState gameState;
    public DialogueMenu(String t){
        this.text = t;
    }
    @Override
    public void initialize(GameState g) {
        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        textFinished = true;

        List<String> textDivision = new ArrayList<>();
        textIndexOne = 0;
        textIndexTwo = textIndexOne + maxLength;
        boolean done = false;
//        while(!done){
//            String temp = "";
//            int remainder = 0;
//            if(textIndexTwo > text.length()){
//                remainder = textIndexTwo - text.length();
//                done = true;
//            }
//            for(int i=textIndexOne; i< textIndexTwo - remainder; i++){
//                temp += text.charAt(i);
//
//            }
//
//            if (text.length() > maxLength){
//                textDivision.add(temp);
//            }
//            //INCREMENT TEXTINDEX VARS HERE
//        }

        Label label = new Label(text.substring(0, maxLength), skin);

        table.add(label);
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
            if (text.length() > maxLength){
                textFinished = false;
                textIndexOne = 0;
                textIndexTwo = maxLength;
                boolean done = false;

            }
            else{
                this.gameState.setPaused(false);
                this.gameState.setMenu(null);
            }
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
