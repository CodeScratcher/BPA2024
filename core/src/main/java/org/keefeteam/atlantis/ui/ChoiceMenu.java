package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A menu where the player selects from options
 */
public class ChoiceMenu implements Menu {
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    private Table menu;
    private BiConsumer<String, GameState> onEntry;
    private List<String> choices;

    /**
     * Creates a choicemenu
     * @param c The list of options
     * @param onEntry A function when an option is chosen, taking in the chosen option and gamestate
     */
    public ChoiceMenu(List<String> c, BiConsumer<String, GameState> onEntry) {
        this.choices = c;
        this.onEntry = onEntry;
    }

    /**
     * Creates a choice menu with a correct option
     * @param choices The list of options
     * @param isCorrect The correct choice
     * @param correct A function to do if the player is correct
     * @param incorrect A function to do if the player is incorrect
     */
    public ChoiceMenu(List<String> choices, String isCorrect, Consumer<GameState> correct, Consumer<GameState> incorrect) {
        this(choices, (str, state) -> {
            if (isCorrect.equals(str)){
                correct.accept(state);
                System.out.println("SUPER REALLY LONG TEXT TO SEE IF THIS WORKS SUPER REALLY LONG TEXT TO SEE IF THIS WORKS SUPER REALLY LONG TEXT TO SEE IF THIS WORKS");
            }
            else incorrect.accept(state);
        });
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

        int i = 0;

        for (String choice : choices) {
            TextButton textButton = new TextButton(choice, skin);
            textButton.addListener(new ClickListener() {
                @Override
                public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    onEntry.accept(choice, gameState);
                    gameState.setMenu(null);
                    gameState.setPaused(false);
                }
            });

            menu.add(textButton);
            i++;
            if (i == 2) {
                i = 0;
                menu.row();
            }
            menu.setFillParent(true);
        }


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
