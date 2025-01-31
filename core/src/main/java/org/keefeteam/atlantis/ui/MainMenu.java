package org.keefeteam.atlantis.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.keefeteam.atlantis.GameState;

import java.util.List;

/**
 * The main menu of the game
 */
public class MainMenu extends ChoiceMenu {
    /**
     * Create a main menu
     */
    public MainMenu() {
        super(List.of("Start", "Help", "Credits", "Exit"), (s, gameState) -> {
            switch (s) {
                case "Help":
                    DialogueMenu dialogueMenu = new DialogueMenu("Explore Atlantis to discover the secrets within. Use WASD to move around, E to interact, and Q to open your inventory. Use your mind, exploration, and experimentation to work out puzzles and give the correct answers.", () -> {
                        gameState.setMenu(new MainMenu());
                        return true;
                    });
                    gameState.setMenu(dialogueMenu);
                    break;
                case "Credits":
                    DialogueMenu dialogueMenu2 = new DialogueMenu("Code by Arielle Fisher, Roman Criscenzo, and Oliver Wilkinson. Design by Roman Criscenzo. Assets either by Oliver Wilkinson, or used with permission from Little Dragon TS and Pixthulhu UI, by Raymond \"Raeleus\" Buckley licensed under CC BY 4.0", () -> {
                        gameState.setMenu(new MainMenu());
                        return true;
                    });
                    gameState.setMenu(dialogueMenu2);
                    break;
                case "Exit":
                    System.exit(0);
            }
        });
        perRow = 1;
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        Label label = new Label("Enter Atlantis", skin, "title");
        Table table = new Table();
        table.add(label).padBottom(50);
        table.row();
        table.add(menu);
        stage.clear();
        stage.addActor(table);
        table.setFillParent(true);
        menu.setFillParent(false);
    }
}
