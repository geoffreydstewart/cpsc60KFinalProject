package org.gds.player;

import org.gds.GameState;

import java.util.Scanner;

public class KeyboardInputPlayer implements Player {

    private final Scanner scanner;

    KeyboardInputPlayer(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void makeMove(char playerColor) {
        GameState gameState = GameState.getInstance();

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
        gameState.playMove(playerColor, columnChoice);
        gameState.incrementTurns();
    }
}
