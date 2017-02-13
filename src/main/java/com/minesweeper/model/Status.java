package com.minesweeper.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    NEW("New game started"),
    SELECT("Select Square row: %s, col: %s"),
    WIN("Congrats you win"),
    LOOSE("Sorry you loose");

    private String value;

    Status(String value) { this.value = value; }

    @JsonValue
    public String getValue() { return this.value; }

    public void setValue(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Status create(String value) {

        for (Status s : Status.values()) {
            if (s.getValue().equals(value)) {
                return s;
            }
        }
        return null;
    }

}