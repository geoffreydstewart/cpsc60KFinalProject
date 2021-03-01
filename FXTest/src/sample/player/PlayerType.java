package sample.player;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PlayerType {

    public static final Integer UI_INPUT = 0;
    public static final Integer COMPUTER_RANDOM = 1;
    public static final Integer COMPUTER_EASY = 2;
    public static final Integer COMPUTER_MEDIUM = 3;
    public static final Integer COMPUTER_HARD = 4;

    public static final int MIN_CHOICE = UI_INPUT;
    public static final int MAX_CHOICE = COMPUTER_HARD;

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

    public static Set<Integer> getKeySet() {
        return playerTypes.keySet();
    }

    public static String getValue(Integer key) {
        return playerTypes.get(key);
    }

}
