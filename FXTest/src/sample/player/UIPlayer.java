package sample.player;


/**
 * This is a Concrete class in the Template design pattern
 */
public class UIPlayer extends AbstractPlayer {

    public UIPlayer(char playerColor) {
        super(playerColor);
        playerNeedsMoveChoice = false;
    }

    @Override
    public int getMoveChoice() {
        int columnChoice = 0;
        //TODO: this isn't need
        return columnChoice;

    }
}
