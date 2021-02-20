package org.gds.player;

import org.gds.GameState;

/**
 * This is the Abstract class in the Template design pattern
 */
public abstract class AbstractPlayer {

    GameState gameState = GameState.getInstance();

    /**
     * This is the template method from the Template design pattern
     * @param playerColor
     */
    public final void move(char playerColor) {
        int columnChoice = getMoveChoice(playerColor);
        completeMove(playerColor, columnChoice);
    }

    abstract int getMoveChoice(char playerColor);

    void completeMove(char playerColor, int columnChoice) {
        gameState.playMove(playerColor, columnChoice);
        gameState.incrementTurns();
    }
}
