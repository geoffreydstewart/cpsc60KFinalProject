package org.gds;

public class GameState {

    private static final GameBoard gameBoard = new GameBoard();

    private static final GameState instance = new GameState();

    private GameState() {
    }

    public static GameState getInstance() {
        return instance;
    }

    public char[][] getGameGrid() {
        return gameBoard.getGameGrid();
    }

    public boolean isGameOver(char playerColor) {
        return gameBoard.isGameOver(playerColor);
    }

    public boolean isValidMove(int columnChoice) {
        return gameBoard.isValidMove(columnChoice);
    }

    public void incrementTurns() {
        gameBoard.incrementTurns();
    }

    public void playMove(char playerColor, int columnChoice) {
        gameBoard.playMove(playerColor, columnChoice);
    }

    public void drawBoard() {
        gameBoard.drawBoard();
    }
}
