package sample.player;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a Concrete class in the Template design pattern
 */
public class RandomPlayer extends AbstractPlayer {

    RandomPlayer(char playerColor) {
        super(playerColor);
        playerNeedsMoveChoice = true;
    }

    @Override
    public int getMoveChoice() {
        // TODO: this 7 needs to be constant
        int columnChoice = ThreadLocalRandom.current().nextInt(0, 7);
        System.out.print("Computer player " + playerColor + " choose column: " + columnChoice);
        System.out.println();
        return columnChoice;
    }
}
