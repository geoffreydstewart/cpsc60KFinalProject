package org.gds.player;

import java.util.Scanner;

public class PlayerFactory {

    private final Scanner scanner;

    public PlayerFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public Player getPlayer(int playerType) {
        if (playerType == PlayerType.KEYBOARD_INPUT) {
            return new KeyboardInputPlayer(scanner);
        }
        else if (playerType == PlayerType.COMPUTER_EASY) {
            return new EasyPlayer();
        }
        else if (playerType == PlayerType.COMPUTER_HARD) {
            // TODO: fix this
            return new EasyPlayer();
        }
        else {
            // TODO: probably throw some exception?
            return new KeyboardInputPlayer(scanner);
        }
    }
}
