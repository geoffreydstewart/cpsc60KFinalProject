package org.gds;

import org.gds.player.PlayerType;
import org.gds.player.AbstractPlayer;
import org.gds.player.PlayerFactory;

import java.util.Map;
import java.util.Scanner;

public class RealOne {

    // TODO: maybe make these private again, or move these
    public final static char RED = 'R';
    public final static char YELLOW = 'Y';

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        PlayerFactory playerFactory = new PlayerFactory(scanner);

        int gameChoice = promptForGameChoice(scanner);
        AbstractPlayer opponentPlayer = playerFactory.getPlayer(gameChoice);
        AbstractPlayer firstPlayer = playerFactory.getPlayer(PlayerType.KEYBOARD_INPUT);
        GameState gameState = GameState.getInstance();

        char playerColor = RED;
        while (!gameState.isGameOver(playerColor)) {
            gameState.drawBoard();

            playerColor = RED;
            firstPlayer.move(playerColor);
            if (gameState.isGameOver(playerColor)) {
                break;
            }
            gameState.drawBoard();

            playerColor = YELLOW;
            opponentPlayer.move(playerColor);
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
}
