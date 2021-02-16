package org.gds.player;

// TODO: Delete this
public enum OldPlayerType {

    KEYBOARD_INPUT(1, "Two player game"),
    COMPUTER_EASY(2, "Game vs. easy computer"),
    COMPUTER_HARD(3, "Game vs. hard computer");

    private int type;
    private String description;

    OldPlayerType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int type() {
        return type;
    }

    public String description() {
        return description;
    }

}

