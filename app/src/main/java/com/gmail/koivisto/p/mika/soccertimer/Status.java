package com.gmail.koivisto.p.mika.soccertimer;

public enum Status {
    NO_ERROR(1, "No error."),
    NO_PLAYER_AT_ALL_IN_LOCATION(2, "Two or More player in same location"),
    DUPLICATE_PLAYER_IN_SAME_LOCATION(3, "Two or More player in same location"),
    PLAYER_ROW_OR_COLUMN_NOT_VALID(4, "Player row or column not valid"),
    SAME_NAME_IS_ALREADY_ADDED(5, "Same Player is try add two times"),
    DUBLICATE_LOCATION_IN_SAME_PLAYER(6, "Playr's same location is set two or More time"),
    NO_PLAYRR_FIND(7, "Player not find"),
    NO_LOCATION_FIND(8, "Player's location not find"),
    NOT_YET_IMPLEMENTED(8, "Implemtation not yet ready");

    private final int code;
    private final String description;

    private Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}