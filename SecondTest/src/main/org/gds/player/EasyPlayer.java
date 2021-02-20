package org.gds.player;

import org.gds.GameBoard;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a Concrete class in the Template design pattern
 */
public class EasyPlayer extends AbstractPlayer {

    @Override
    public int getMoveChoice(char playerColor) {
        int columnChoice = ThreadLocalRandom.current().nextInt(0, GameBoard.NUM_COLS);
        System.out.print("Computer player " + playerColor + " choose column: " + columnChoice);
        System.out.println();
        return columnChoice;
    }
}
