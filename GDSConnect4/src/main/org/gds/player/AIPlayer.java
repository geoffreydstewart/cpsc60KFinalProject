package org.gds.player;


/**
 * This is a Concrete class in the Template design pattern
 */
public class AIPlayer extends AbstractPlayer {

    private final int searchDepth;
    String playerColor;

    public AIPlayer(int searchDepth, String playerColor) {
        playerMustChooseMove = true;
        this.searchDepth = searchDepth;
        this.playerColor = playerColor;
    }

    @Override
    public int makeChoice() {
        return 0;
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
