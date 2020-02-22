package com.gmail.koivisto.p.mika.soccertimer;

public enum Status {
    NO_ERROR(1, "No error."),
    NO_PLAYER_AT_ALL_IN_LOCATION(2, "Two or More player in same location"),
    DUPLICATE_PLAYER_IN_SAME_LOCATION(2, "Two or More player in same location");

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