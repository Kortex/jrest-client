package com.ariskourt.restclient.exceptions;

public class RestClientException extends RuntimeException {

    public RestClientException() {
        super();
    }

    public RestClientException(Throwable cause) {
	super(cause);
    }

    public RestClientException(String message) {
        super(message);
    }

}
