package org.gds.model.player;

import org.gds.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerFactoryTest {

    /**
     * While the Player Factory wasn't a REAL Design Pattern, but more of a programming idiom
     * according to the course text, we'll still unit test that. Essentially, we just test that the
     * simple factory {@link PlayerFactory} returns the correct class.
     */
    @Test
    void testPlayerFactory() {
        AbstractPlayer player = PlayerFactory.getPlayer(PlayerType.UI_INPUT, Constants.RED);
        assertTrue(player instanceof UIPlayer);
        assertFalse(player.isPlayerMustChooseMove());

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_RANDOM, Constants.RED);
        assertTrue(player instanceof RandomPlayer);
        assertTrue(player.isPlayerMustChooseMove());

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_EASY, Constants.YELLOW);
        assertTrue(player instanceof AIPlayer);
        assertTrue(player.isPlayerMustChooseMove());

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_MEDIUM, Constants.YELLOW);
        assertTrue(player instanceof AIPlayer);
        assertTrue(player.isPlayerMustChooseMove());

        player = PlayerFactory.getPlayer(PlayerType.COMPUTER_HARD, Constants.RED);
        assertTrue(player instanceof AIPlayer);
        assertTrue(player.isPlayerMustChooseMove());
    }
}
