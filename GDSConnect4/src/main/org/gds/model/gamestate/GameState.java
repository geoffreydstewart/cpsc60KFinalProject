package org.gds.model.gamestate;

import org.gds.model.disc.Disc;

/**
 * This class implements the Singleton Design Pattern, to ensure that
 * only a single instance of the game state representation may exist.
 *
 * This class is also the Proxy class in the Proxy Design Pattern. It is
 * the surrogate to the GameBoardImpl class.
 *
 */
public class GameState implements GameBoard {

    private static final GameBoard gameBoard = new GameBoardImpl();

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
