package org.keefeteam.atlantis.ui;

import java.util.List;

/**
 * The main menu of the game
 */
public class MainMenu extends ChoiceMenu {
    public MainMenu() {
        super(List.of("Start", "Help", "Exit"), (s, gameState) -> {
            switch (s) {
                case "Start":
                    break;
                case "Help":
                    DialogueMenu dialogueMenu = new DialogueMenu("", () -> {
                        gameState.setMenu(new MainMenu());
                        return true;
                    });
                    break;
                case "Exit":
                    System.exit(0);
            }
        });

    }
}
