package org.gds.model.player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerType {

    public static final Integer UI_INPUT = 0;
    public static final Integer COMPUTER_RANDOM = 1;
    public static final Integer COMPUTER_EASY = 2;
    public static final Integer COMPUTER_MEDIUM = 3;
    public static final Integer COMPUTER_HARD = 4;

    private static final String UI_INPUT_DESC = "Two player game";
    private static final String COMPUTER_RANDOM_DESC = "Game vs. random computer";
    private static final String COMPUTER_EASY_DESC = "Game vs. easy computer";
    private static final String COMPUTER_MEDIUM_DESC = "Game vs. medium computer";
    private static final String COMPUTER_HARD_DESC = "Game vs. hard computer";

    // use a sorted map implementation to maintain an insertion ordering of the keys
    private final static Map<Integer, String> playerTypes = new LinkedHashMap<>();
    static {
        playerTypes.put(UI_INPUT, UI_INPUT_DESC);
        playerTypes.put(COMPUTER_RANDOM, COMPUTER_RANDOM_DESC);
        playerTypes.put(COMPUTER_EASY, COMPUTER_EASY_DESC);
        playerTypes.put(COMPUTER_MEDIUM, COMPUTER_MEDIUM_DESC);
        playerTypes.put(COMPUTER_HARD, COMPUTER_HARD_DESC);
    }

    public static Map<Integer, String> getPlayerTypes() {
        return playerTypes;
    }

}
