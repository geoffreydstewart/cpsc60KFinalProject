package org.gds;

public class GameState {

    public static final int NUM_ROWS = 6;
    public static final int NUM_COLS = 7;

    private static final int MAX_TURNS = 42;

    private static final char[][] gameGrid = new char[NUM_ROWS][NUM_COLS];
    static {
        for (int row = 0; row < gameGrid.length; row++) {
            for (int col = 0; col < gameGrid[0].length; col++) {
                gameGrid[row][col] = ' ';
            }
        }
    }

    private static final GameState instance = new GameState();

    private int numTurns = 0;

    private GameState() {
    }

    public static GameState getInstance() {
        return instance;
    }

    public char[][] getGameGrid() {
        return gameGrid;
    }

    public boolean isGameOver(char playerColor) {
        if (numTurns >= MAX_TURNS) {
            System.out.println("It's a tie!");
            return true;
        }
        else {
            //check for 4 across
            for(int row = 0; row<gameGrid.length; row++){
                for (int col = 0;col < gameGrid[0].length - 3;col++){
                    if (gameGrid[row][col] == playerColor   && gameGrid[row][col+1] == playerColor &&
                        gameGrid[row][col+2] == playerColor && gameGrid[row][col+3] == playerColor){
                        System.out.println("Player " + playerColor + " won!");
                        return true;
                    }
                }
            }
            //check for 4 up and down
            for(int row = 0; row < gameGrid.length - 3; row++){
                for(int col = 0; col < gameGrid[0].length; col++){
                    if (gameGrid[row][col] == playerColor   && gameGrid[row+1][col] == playerColor &&
                        gameGrid[row+2][col] == playerColor && gameGrid[row+3][col] == playerColor){
                        System.out.println("Player " + playerColor + " won!");
                        return true;
                    }
                }
            }
            //check upward diagonal
            for(int row = 3; row < gameGrid.length; row++){
                for(int col = 0; col < gameGrid[0].length - 3; col++){
                    if (gameGrid[row][col] == playerColor   && gameGrid[row-1][col+1] == playerColor &&
                        gameGrid[row-2][col+2] == playerColor && gameGrid[row-3][col+3] == playerColor){
                        System.out.println("Player " + playerColor + " won!");
                        return true;
                    }
                }
            }
            //check downward diagonal
            for(int row = 0; row < gameGrid.length - 3; row++){
                for(int col = 0; col < gameGrid[0].length - 3; col++){
                    if (gameGrid[row][col] == playerColor && gameGrid[row+1][col+1] == playerColor &&
                        gameGrid[row+2][col+2] == playerColor && gameGrid[row+3][col+3] == playerColor){
                        System.out.println("Player " + playerColor + " won!");
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public boolean isValidMove(int columnChoice) {
        // is the columnChoice valid
        if (columnChoice < 0 || columnChoice > gameGrid[0].length){
            return false;
        }

        // check if the column is full
        if (gameGrid[0][columnChoice] != ' '){
            return false;
        }

        return true;
    }

    public void incrementTurns() {
        numTurns++;
    }

    public void playMove(char playerColor, int columnChoice) {
        for (int row = gameGrid.length-1; row >= 0; row--){
            if(gameGrid[row][columnChoice] == ' '){
                gameGrid[row][columnChoice] = playerColor;
                break;
            }
        }
    }
}
