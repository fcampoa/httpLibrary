package org.exceptions;

public class PathNamingException  extends Exception{
    public PathNamingException(){}
    public PathNamingException(String msg) { super(msg); }
    public PathNamingException(Throwable t) { super(t); }
    public PathNamingException(String msg, Throwable t) { super(msg, t); }
}