package sample.player;


/**
 * This is a Concrete class in the Template design pattern
 */
public class AIPlayer extends AbstractPlayer {

    private final int searchDepth;

    public AIPlayer(int searchDepth, char playerColor) {
        super(playerColor);
        playerNeedsMoveChoice = true;
        this.searchDepth = searchDepth;
    }

    @Override
    public int getMoveChoice() {
        int columnChoice = 0;
        boolean validMove = false;
        while (!validMove) {
            System.out.print("Player " + playerColor + ", please choose a column:");
            System.out.println();
            //columnChoice = scanner.nextInt();
//            if (gameState.isValidMove(columnChoice)) {
            validMove = true;
            System.out.print("Player " + playerColor + " chose column: " + columnChoice);
            System.out.println();
/*            }
            else  {
                System.out.print("Player " + playerColor + "! Choose a valid column!");
                System.out.println();
            }*/
        }
        return columnChoice;

    }
}
