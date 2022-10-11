package org.util;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS");
    private String value;
    HttpMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
