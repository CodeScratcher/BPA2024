package org.keefeteam.atlantis.ui;

import java.util.List;

/**
 * The main menu of the game
 */
public class MainMenu extends ChoiceMenu {
    public MainMenu() {
        super(List.of("Start", "Help", "Exit"), (s, gameState) -> {
            switch (s) {
                case "Help":
                    DialogueMenu dialogueMenu = new DialogueMenu("INSERT HELP TEXT HERE", () -> {
                        gameState.setMenu(new MainMenu());
                        return true;
                    });
                    gameState.setMenu(dialogueMenu);
                    break;
                case "Exit":
                    System.exit(0);
            }
        });

    }
}
