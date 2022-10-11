package org.util;

import java.io.Serializable;

public class Request implements Serializable {

    private String method;
    private Object body;

    public Request() { }
    public Request(Object body) {
        this.body = body;
    }

    public Request(Object body, String method) {
        this.method = method;
        this.body = body;
    }
    public String getMethod() {
        return this.method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public Object getBody() {
        return this.body;
    }
    public void setBody(Object body) {
        this.body = body;
    }
}
