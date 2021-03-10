package org.gds;

import javafx.scene.layout.Pane;
import org.gds.model.disc.Disc;
import org.gds.model.disc.UIDisc;
import org.gds.model.gamestate.GameState;
import org.gds.model.player.AbstractPlayer;
import org.gds.model.player.PlayerFactory;
import org.gds.model.player.PlayerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * To demonstrate the correct implementation of the Model View Controller Pattern,
 * we can unit test the full functionality of the game using the {@link GameController}.
 * This illustrates that the View and the Controller are loosely coupled, and View
 * and Model are decoupled.
 */
public class GameControllerTest {

    @AfterEach
    void tearDown() {
        GameState.getInstance().reset();
    }

    /**
     * This tests plays a game against Hard Computer, where we choose choose column 0, when
     * the number of turns we have taken is even, and column 6 when then number of turns we
     * have taken is odd. The Hard Computer will win this game everytime.
     */
    @Test
    void testPlayerVsHardComputer() {
        Pane discPane = new Pane();
        GameController gameController = new GameController(discPane);
        gameController.initializeOpponentPlayer(PlayerType.COMPUTER_HARD);

        boolean gameEnded = false;
        int ourNumTurns = 0;
        while (!gameEnded) {
            int columnChoice = (ourNumTurns % 2) == 0 ? 0 : 6;
            ourNumTurns++;

            // play our move - passing true creates a RED disc
            Disc disc = new UIDisc(true);
            gameController.placeDisc(disc, columnChoice);

            // The computer plays their move - passing false creates a YELLOW disc
            Disc opponentDisc = new UIDisc(false);
            gameEnded = gameController.placeComputerOpponentDisc(opponentDisc);
        }

        String winningColor = GameState.getInstance().isRedMove() ? Constants.RED : Constants.YELLOW;
        assertEquals(winningColor, Constants.YELLOW);
    }

    /**
     * This tests plays a game of the Random Computer player against Hard Computer. That is
     * hardly a fair game! Again, the Hard Computer will win this game everytime.
     */
    @Test
    void testRandomComputerVsHardComputer() {
        Pane discPane = new Pane();
        GameController gameController = new GameController(discPane);
        AbstractPlayer randomPlayer = PlayerFactory.getPlayer(PlayerType.COMPUTER_RANDOM, Constants.RED);
        gameController.initializeOpponentPlayer(PlayerType.COMPUTER_HARD);

        boolean gameEnded = false;
        while (!gameEnded) {
            // play Random Computer's move - passing true creates a RED disc
            Disc disc = new UIDisc(true);
            int columnChoice = randomPlayer.getColumnChoice();
            gameController.placeDisc(disc, columnChoice);

            // The computer plays their move - passing false creates a YELLOW disc
            Disc opponentDisc = new UIDisc(false);
            gameEnded = gameController.placeComputerOpponentDisc(opponentDisc);
        }

        String winningColor = GameState.getInstance().isRedMove() ? Constants.RED : Constants.YELLOW;
        assertEquals(winningColor, Constants.YELLOW);
    }
}
