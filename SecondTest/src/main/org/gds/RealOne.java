package org.gds;

import org.gds.player.PlayerType;
import org.gds.player.Player;
import org.gds.player.PlayerFactory;

import java.util.Map;
import java.util.Scanner;

public class RealOne {

    private final static char RED = 'R';
    private final static char YELLOW = 'Y';

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        PlayerFactory playerFactory = new PlayerFactory(scanner);

        int gameChoice = promptForGameChoice(scanner);
        Player opponentPlayer = playerFactory.getPlayer(gameChoice);
        Player firstPlayer = playerFactory.getPlayer(PlayerType.KEYBOARD_INPUT);
        GameState gameState = GameState.getInstance();

        char playerColor = RED;
        while (!gameState.isGameOver(playerColor)) {
            drawBoard();

            playerColor = RED;
            firstPlayer.makeMove(playerColor);
            if (gameState.isGameOver(playerColor)) {
                break;
            }
            drawBoard();

            playerColor = YELLOW;
            opponentPlayer.makeMove(playerColor);
        }
    }

    private static int promptForGameChoice(Scanner scanner) {
        int gameChoice = 0;
        while ((gameChoice < PlayerType.MIN_CHOICE) || (gameChoice > PlayerType.MAX_CHOICE)) {
            System.out.println();
            for (Map.Entry<Integer, String> p : PlayerType.getEntrySet()) {
                System.out.println(p.getKey() + ") " + p.getValue());
            }
            System.out.println("What type of game do you want to play " + PlayerType.getKeySet() + "?");
            gameChoice = scanner.nextInt();
        }
        System.out.println();
        System.out.println("You choose option: " + gameChoice + ") " + PlayerType.getValue(gameChoice));
        return gameChoice;
    }

    private static void drawBoard() {
        char[][] grid = GameState.getInstance().getGameGrid();

        System.out.println();
        System.out.println("---------------");
        for (int row = 0; row < grid.length; row++){
            System.out.print("|");
            for (int col = 0; col < grid[0].length; col++){
                System.out.print(grid[row][col]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println();
    }
}
