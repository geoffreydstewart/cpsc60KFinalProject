package sample.player;

public class PlayerFactory {

    // TODO: It could be an issue if this is static
    public static AbstractPlayer getPlayer(int playerType, char playerColor) {
        if (playerType == PlayerType.UI_INPUT) {
            return new UIPlayer(playerColor);
        }
        else if (playerType == PlayerType.COMPUTER_RANDOM) {
            return new RandomPlayer(playerColor);
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
