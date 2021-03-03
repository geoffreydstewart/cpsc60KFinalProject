package org.gds.player;

/**
 * This is the Abstract class in the Template design pattern
 */
public abstract class AbstractPlayer {

    public final static int DUMMY_COLUMN_CHOICE = -1;

    boolean playerMustChooseMove;

    public AbstractPlayer() {
    }

    /**
     * This is the template method from the Template design pattern
     */
    public final int getColumnChoice() {
        if (playerMustChooseMove) {
            pauseForEffect();
            return makeChoice();
        }
        else {
            return DUMMY_COLUMN_CHOICE;
        }
    }

    abstract int makeChoice();

    abstract void pauseForEffect();

}

