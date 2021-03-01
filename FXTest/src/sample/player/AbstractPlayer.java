package sample.player;

/**
 * This is the Abstract class in the Template design pattern
 */
public abstract class AbstractPlayer {

    char playerColor;
    boolean playerNeedsMoveChoice;

    public AbstractPlayer() {
    }

    AbstractPlayer(char playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * This is the template method from the Template design pattern
     */
//    public final void move() {
//        int columnChoice = getMoveChoice();
//        completeMove(columnChoice);
//    }

    public boolean isPlayerNeedsMoveChoice(){
        return playerNeedsMoveChoice;
    }

    public abstract int getMoveChoice();


}

