package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.*;
import java.util.function.Supplier;

/**
 * A menu that just displays text
 */
public class DialogueMenu implements Menu {
    // The text that is being displayed
    private String text;
    // the maximum number of characters to be displayed
    private int maxLength;
    // where the text starts
    private int textIndexOne;
    // where the text ends
    private int textIndexTwo;
    // checks if there is no more text to display
    private boolean textFinished;
    boolean inputControl;

    private Stage stage;
    private Table table;
    private Skin skin;
    private GameState gameState;

    //Where the text is printed
    private Label textLabel;

    private Supplier<Boolean> onEnd;

    /**
     * Create a dialogue menu
     * @param t The text of the dialogue menu
     */
    public DialogueMenu(String t){
        this.text = t;
        this.onEnd = () -> false;
    }

    /**
     * Create a dialogue menu that does something on completion
     * @param text The text
     * @param onEnd The function to run on completion
     */
    public DialogueMenu(String text, Supplier<Boolean> onEnd) {
        this.text = text;
        this.onEnd = onEnd;
    }

    @Override
    public void initialize(GameState g) {

        maxLength = 50;
        this.gameState = g;
        this.gameState.setPaused(true);
        stage = new Stage();

        BitmapFont font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("ui/pixthulhu-ui.json"));
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
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void update(Set<InputEvent> inputEvents) {
        if (inputEvents.contains(InputEvent.Interact)) {
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
                table.remove(); // Safely remove the old table
                textLabel = new Label(newValue, skin);
                table = new Table();
                table.setFillParent(true);
                table.add(textLabel);
                stage.addActor(table);
            }
            else{
                //Destroy label
                if (onEnd.get()) return;
                table.remove();
                gameState.setMenu(null);
                this.gameState.setPaused(false);
            }
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
