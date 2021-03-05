package org.gds.model.player;

import org.gds.model.gamestate.GameState;

/**
 * This is the Abstract class in the Template design pattern
 */
public abstract class AbstractPlayer {

    public final static int INVALID_COLUMN_CHOICE = -1;

    boolean playerMustChooseMove;

    public AbstractPlayer() {
    }

    public boolean isPlayerMustChooseMove() {
        return playerMustChooseMove;
    }

    /**
     * This is the template method from the Template design pattern
     */
    public final int getColumnChoice() {
        if (playerMustChooseMove) {
            int columnChoice = makeChoice();
            while (!isValidChoice(columnChoice)) {
                columnChoice = makeChoice();
            }
            return columnChoice;
        }
        else {
            return INVALID_COLUMN_CHOICE;
        }
    }

    abstract int makeChoice();

    boolean isValidChoice(int columnChoice) {
        return GameState.getInstance().isValidMove(columnChoice);
    }

}

