package org.gds.player;

public class PlayerFactory {

    // TODO: Document this
    public static AbstractPlayer getPlayer(int playerType, String playerColor) {
        if (playerType == PlayerType.UI_INPUT) {
            return new UIPlayer();
        }
        else if (playerType == PlayerType.COMPUTER_RANDOM) {
            return new RandomPlayer();
        }
        else if (playerType == PlayerType.COMPUTER_EASY) {
            return new AIPlayer(2, playerColor);
        }
        else if (playerType == PlayerType.COMPUTER_MEDIUM) {
            return new AIPlayer(3, playerColor);
        }
        else {
            return new AIPlayer(6, playerColor);
        }
    }
}
