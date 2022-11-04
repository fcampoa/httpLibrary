package org.exceptions;

public class StartupException extends Exception{
    public StartupException(){}
    public StartupException(String msg) { super(msg); }
    public StartupException(Throwable t) { super(t); }
    public StartupException(String msg, Throwable t) { super(msg, t); }
}
