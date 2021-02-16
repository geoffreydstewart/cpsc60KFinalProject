package org.gds.player;

import org.gds.GameState;

import java.util.concurrent.ThreadLocalRandom;

public class EasyPlayer implements Player {

    @Override
    public void makeMove(char playerColor) {
        GameState gameState = GameState.getInstance();
        int columnChoice = ThreadLocalRandom.current().nextInt(0, GameState.NUM_COLS + 1);
        System.out.print("Computer player " + playerColor + " choose column: " + columnChoice);
        System.out.println();
        gameState.playMove(playerColor, columnChoice);
        gameState.incrementTurns();
    }
}
