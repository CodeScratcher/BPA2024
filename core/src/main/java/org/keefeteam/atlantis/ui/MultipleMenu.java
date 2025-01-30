package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.keefeteam.atlantis.GameState;
import org.keefeteam.atlantis.util.input.InputEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A menu where the player selects from options
 */
public class MultipleMenu implements Menu {
    private GameState gameState;
    private Stage stage;
    private Skin skin;
    private Table menu;
    private BiConsumer<List<String> , GameState> onEntry;
    private List<String> choices;
    private List<TextButton> buttons;

    /**
     * Creates a multiple choice menu
     * @param c The list of options
     * @param onEntry A function when an option is chosen, taking in the selected options and gamestate
     */
    public MultipleMenu(List<String> c, BiConsumer<List<String>, GameState> onEntry) {
        this.choices = c;
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
        Table options = new Table();
        stage.addActor(menu);

        int i = 0;

        buttons = new ArrayList<>();
        for (String choice : choices) {
            TextButton textButton = new TextButton(choice, skin, "toggle");

            options.add(textButton);
            buttons.add(textButton);

            i++;
            if (i == 3) {
                i = 0;
                options.row();
            }
        }

        menu.add(options);

        menu.row();
        menu.setFillParent(true);


        TextButton textButton = new TextButton("Submit", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gameState.setMenu(null);
                gameState.setPaused(false);

                List<String> choice = new ArrayList<>();

                for (TextButton button : buttons) {
                    if (button.isChecked()) {
                        choice.add(button.getLabel().getText().toString());
                    }
                }

                onEntry.accept(choice, gameState);
            }
        });

        menu.add(textButton);

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
