package org.util;

public enum HttpStatus {

    OK(200, "Ok"),
    NOT_FOUND(400, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int value;
    private final String name;

    HttpStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() { return this.value; }
    private String getName() { return this.name; }

}
