package org.gds.player;

import org.gds.GameState;

/**
 * This is the Abstract class in the Template design pattern
 */
public abstract class AbstractPlayer {

    GameState gameState = GameState.getInstance();

    final char playerColor;

    AbstractPlayer(char playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * This is the template method from the Template design pattern
     */
    public final void move() {
        int columnChoice = getMoveChoice();
        completeMove(columnChoice);
    }

    abstract int getMoveChoice();

    void completeMove(int columnChoice) {
        gameState.playMove(playerColor, columnChoice);
        gameState.incrementTurns();
    }
}
