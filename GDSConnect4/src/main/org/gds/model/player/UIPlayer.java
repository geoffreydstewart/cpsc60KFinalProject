package org.gds.model.player;


/**
 * This is a Concrete class in the Template design pattern
 */
public class UIPlayer extends AbstractPlayer {

    public UIPlayer() {
        playerMustChooseMove = false;
    }

    @Override
    int makeChoice() {
        return INVALID_COLUMN_CHOICE;
    }

}
