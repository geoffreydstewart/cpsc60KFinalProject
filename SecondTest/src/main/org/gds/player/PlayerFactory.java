package org.gds.player;

import java.util.Scanner;

public class PlayerFactory {

    private final Scanner scanner;

    public PlayerFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public AbstractPlayer getPlayer(int playerType, char playerColor) {
        if (playerType == PlayerType.KEYBOARD_INPUT) {
            return new KeyboardInputPlayer(scanner, playerColor);
        }
        else if (playerType == PlayerType.COMPUTER_RANDOM) {
            return new RandomPlayer(playerColor);
        }
        else if (playerType == PlayerType.COMPUTER_EASY) {
            return new AIPlayer(2, playerColor);
        }
        else if (playerType == PlayerType.COMPUTER_MEDIUM) {
            return new AIPlayer(3, playerColor);
        }
        else {
            return new AIPlayer(6, playerColor);
        }
    }
}
