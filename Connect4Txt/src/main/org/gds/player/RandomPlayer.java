package org.gds.player;

import org.gds.GameBoard;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a Concrete class in the Template design pattern
 */
public class RandomPlayer extends AbstractPlayer {

    RandomPlayer(char playerColor) {
        super(playerColor);
    }

    @Override
    public int getMoveChoice() {
        int columnChoice = ThreadLocalRandom.current().nextInt(0, GameBoard.NUM_COLS);
        System.out.print("Computer player " + playerColor + " choose column: " + columnChoice);
        System.out.println();
        return columnChoice;
    }
}
