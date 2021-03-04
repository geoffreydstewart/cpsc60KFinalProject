package org.gds.model;

import org.gds.model.disc.Disc;

public class GameState {

    private static final GameBoard gameBoard = new GameBoard();

    private static final GameState instance = new GameState();

    private GameState() {
    }

    public static GameState getInstance() {
        return instance;
    }

    public boolean isRedMove() {
        return gameBoard.isRedMove();
    }

    public void toggleRedMove() {
        gameBoard.toggleRedMove();
    }

    public Disc[][] getGameGrid() {
        return gameBoard.getGameGrid();
    }

    public int placeDisc(Disc disc, int column) {
        return gameBoard.placeDisc(disc, column);
    }

    public boolean isValidMove(int columnChoice) {
        return gameBoard.isValidMove(columnChoice);
    }

    public boolean isGameOver(int column, int row) {
        return gameBoard.isGameOver(column, row);
    }

    public boolean noMoreTurns() {
        return gameBoard.noMoreTurns();
    }

    public void reset() {
        gameBoard.reset();
    }

}
