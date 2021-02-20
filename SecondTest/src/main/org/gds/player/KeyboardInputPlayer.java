package org.gds.player;

import java.util.Scanner;

/**
 * This is a Concrete class in the Template design pattern
 */
public class KeyboardInputPlayer extends AbstractPlayer {

    private final Scanner scanner;

    KeyboardInputPlayer(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public int getMoveChoice(char playerColor) {
        int columnChoice = 0;
        boolean validMove = false;
        while (!validMove) {
            System.out.print("Player " + playerColor + ", please choose a column:");
            System.out.println();
            columnChoice = scanner.nextInt();
            if (gameState.isValidMove(columnChoice)) {
                validMove = true;
                System.out.print("Player " + playerColor + " chose column: " + columnChoice);
                System.out.println();
            }
            else  {
                System.out.print("Player " + playerColor + "! Choose a valid column!");
                System.out.println();
            }
        }
        return columnChoice;

    }
}
