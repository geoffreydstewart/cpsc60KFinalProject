package org.gds.player;


/**
 * This is a Concrete class in the Template design pattern
 */
public class UIPlayer extends AbstractPlayer {

    public UIPlayer() {
        playerMustChooseMove = false;
    }

    @Override
    int makeChoice() {
        return DUMMY_COLUMN_CHOICE;
    }

    @Override
    void pauseForEffect() {
        // The UI Player doesn't need any pause to give the gameplay a realistic feeling
        return;
    }

}
