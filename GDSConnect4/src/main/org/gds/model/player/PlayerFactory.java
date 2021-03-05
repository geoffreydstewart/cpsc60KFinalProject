package org.gds.model.player;

/**
 * This is the PlayerFactory. As a simple factory, it's not a REAL Design Pattern,
 * but more of a programming idiom according to the course text.
 */
public class PlayerFactory {

    public static AbstractPlayer getPlayer(int playerType, String playerColor) {
        if (playerType == PlayerType.UI_INPUT) {
            return new UIPlayer();
        }
        else if (playerType == PlayerType.COMPUTER_RANDOM) {
            return new RandomPlayer();
        }
        else if (playerType == PlayerType.COMPUTER_EASY) {
            return new AIPlayer(1, playerColor);
        }
        else if (playerType == PlayerType.COMPUTER_MEDIUM) {
            return new AIPlayer(3, playerColor);
        }
        else {
            return new AIPlayer(6, playerColor);
        }
    }
}
