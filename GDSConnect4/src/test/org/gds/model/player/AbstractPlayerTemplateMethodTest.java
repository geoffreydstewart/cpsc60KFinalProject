package org.gds.model.player;

import org.gds.Constants;
import org.gds.model.gamestate.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractPlayerTemplateMethodTest {
    
    private static final int MAX_NUM_CHOICES = 100;

    /**
     * Tests the Template Method Design Pattern for the template method {@link AbstractPlayer#getColumnChoice()}
     */
    @Test
    void testAbstractPlayerTemplateMethod() {
        AbstractPlayer player = PlayerFactory.getPlayer(PlayerType.UI_INPUT, Constants.YELLOW);
        int columnChoice = player.getColumnChoice();
        // A UI player depends on the player to use the UI to choose the move. Calling this method
        // should return an invalid column.
        assertEquals(columnChoice, AbstractPlayer.INVALID_COLUMN_CHOICE);

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_RANDOM, Constants.RED);
        // Test that the Computer Random player chooses a valid move everytime 
        for (int i = 0; i < MAX_NUM_CHOICES; i++) {
            columnChoice = player.getColumnChoice();
            assertTrue(GameState.getInstance().isValidMove(columnChoice));
        }

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_EASY, Constants.RED);
        // Test that the Easy Commputer chooses a valid move everytime
        for (int i = 0; i < MAX_NUM_CHOICES; i++) {
            columnChoice = player.getColumnChoice();
            assertTrue(GameState.getInstance().isValidMove(columnChoice));
        }

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_MEDIUM, Constants.RED);
        // Test that the Medium Commputer chooses a valid move everytime
        for (int i = 0; i < MAX_NUM_CHOICES; i++) {
            columnChoice = player.getColumnChoice();
            assertTrue(GameState.getInstance().isValidMove(columnChoice));
        }

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_HARD, Constants.RED);
        // Test that the Hard Commputer chooses a valid move everytime
        for (int i = 0; i < MAX_NUM_CHOICES; i++) {
            columnChoice = player.getColumnChoice();
            assertTrue(GameState.getInstance().isValidMove(columnChoice));
        }
    }
}
