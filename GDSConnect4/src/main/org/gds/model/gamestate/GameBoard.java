package org.gds.model.gamestate;

import org.gds.model.disc.Disc;

/**
 * This class is the Subject interface in the Proxy Design Pattern
 */
public interface GameBoard {

    boolean isRedMove();

    void toggleRedMove();

    Disc[][] getGameGrid();

    int placeDisc(Disc disc, int column);

    boolean isValidMove(int columnChoice);

    boolean isGameOver(int column, int row);

    boolean noMoreTurns();

    void reset();
}
