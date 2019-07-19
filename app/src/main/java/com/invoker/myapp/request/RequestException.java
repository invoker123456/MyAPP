package com.invoker.myapp.request;

/**
 * Created by Hioak on 2015/12/4.
 */
public class RequestException extends Exception {
    public RequestException() {
    }

    public RequestException(String detailMessage) {
        super(detailMessage);
    }

    public RequestException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RequestException(Throwable throwable) {
        super(throwable);
    }
}
