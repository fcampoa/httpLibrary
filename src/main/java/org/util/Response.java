package org.util;

import java.io.Serializable;

public class Response implements Serializable {
    private Object data;
    private int status;

    public Response() {

    }

    public Response(Object data, int status) {
        this.data = data;
        this.status = status;
    }
    public Response(Object data) {
        this.data = data;
    }
    public Object getData() {
        return this.data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
