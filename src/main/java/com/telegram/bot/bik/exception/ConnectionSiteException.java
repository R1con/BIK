package com.telegram.bot.bik.exception;

public class ConnectionSiteException extends RuntimeException{
    public ConnectionSiteException() {
        super();
    }

    public ConnectionSiteException(String message) {
        super(message);
    }
}
