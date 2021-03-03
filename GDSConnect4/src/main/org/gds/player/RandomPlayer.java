package org.gds.player;

import org.gds.Constants;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is a Concrete class in the Template design pattern
 */
public class RandomPlayer extends AbstractPlayer {

    RandomPlayer() {
        playerMustChooseMove = true;
    }

    @Override
    public int makeChoice() {
        return ThreadLocalRandom.current().nextInt(0, Constants.COLUMNS);
    }

    @Override
    void pauseForEffect() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Hmm, that's weird, The sleeping thread was interrupted");
        }
    }
}
