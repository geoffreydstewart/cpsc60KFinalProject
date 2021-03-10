package org.gds.model.gamestate;

import org.gds.Constants;
import org.gds.model.disc.Disc;
import org.gds.model.disc.VirtualDisc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@link GameState} class implements the Singleton Design Pattern, and
 * is also the Proxy class from the Proxy Design Pattern. The tests included
 * below test these Design Patterns.
 */
public class GameStateTest {

    @AfterEach
    void tearDown() {
        GameState.getInstance().reset();
    }

    /**
     * Test the Singleton Design Pattern for the GameState Singleton.
     * This requires verifying the state is being maintained correctly.
     */
    @Test
    void testGameStateSingleton() {
        GameState gameState = GameState.getInstance();

        assertTrue(gameState.isRedMove());

        int columnChoice = 0;
        Disc disc = new VirtualDisc(true);
        int row = gameState.placeDisc(disc, columnChoice);
        Disc[][] grid = gameState.getGameGrid();
        assertTrue(grid[0][5].isRed());

        gameState.toggleRedMove();
        assertFalse(gameState.isRedMove());

        // All the columns should be valid moves
        for (int i = 0; i < Constants.COLUMNS; i++) {
            assertTrue(gameState.isValidMove(i));
        }
        // Check the values just outside the gameboard boundaries
        assertFalse(gameState.isValidMove(-1));
        assertFalse(gameState.isValidMove(Constants.COLUMNS));

        assertFalse(gameState.noMoreTurns());
        assertFalse(gameState.isGameOver(columnChoice, row));
    }

    /**
     * Test the Proxy Design Pattern, where the Subject is the {@link GameBoardImpl}
     * and the Proxy is {@link GameState}. The Proxy controls access to an instance
     * of the Subject, so the test will demonstrate that performing the same changes
     * on the GameState Singleton and a separate instance of GameBoardImpl will
     * produce the identical result.
     */
    @Test
    void testGameStateProxy() {
        GameState gameState = GameState.getInstance();
        GameBoardImpl gameBoard = new GameBoardImpl();

        assertEquals(gameState.isRedMove(), gameBoard.isRedMove());

        int columnChoice = 0;
        Disc disc = new VirtualDisc(true);  //Red disc
        int gsRow = gameState.placeDisc(disc, columnChoice);
        int gbRow = gameBoard.placeDisc(disc, columnChoice);
        Disc[][] gsGrid = gameState.getGameGrid();
        Disc[][] gbGrid = gameBoard.getGameGrid();

        assertEquals(gsRow, gbRow);
        assertEquals(gsGrid[0][5].isRed(), gbGrid[0][5].isRed());

        columnChoice = 1;
        disc = new VirtualDisc(false);  //Yellow disc
        gsRow = gameState.placeDisc(disc, columnChoice);
        gbRow = gameBoard.placeDisc(disc, columnChoice);

        assertEquals(gsRow, gbRow);
        assertEquals(gsGrid[1][5].isRed(), gbGrid[1][5].isRed());

        gameState.toggleRedMove();
        gameBoard.toggleRedMove();
        assertEquals(gameState.isRedMove(), gameBoard.isRedMove());

        // All the columns should be valid moves
        for (int i = 0; i < Constants.COLUMNS; i++) {
            assertEquals(gameState.isValidMove(i), gameBoard.isValidMove(i));
        }
        // Check the values just outside the gameboard boundaries
        assertEquals(gameState.isValidMove(-1), gameBoard.isValidMove(-1));
        assertEquals(gameState.isValidMove(Constants.COLUMNS), gameBoard.isValidMove(Constants.COLUMNS));

        assertEquals(gameState.noMoreTurns(), gameBoard.noMoreTurns());
        assertEquals(gameState.isGameOver(columnChoice, gsRow), gameBoard.isGameOver(columnChoice, gbRow));
    }
}
