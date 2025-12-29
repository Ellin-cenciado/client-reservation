package com.reservation.common.model;


public enum Work {
    PIERCING("Piercing"),
    TATTOO("Tattoo"),
    REVISION("Revision"),
    CHANGE("Change"),
    OTHER("Other");

    private final String displayName;

    Work(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}