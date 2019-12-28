package com.ariskourt.restclient.resources;

import com.ariskourt.restclient.exceptions.RestClientException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestResponseTest {

    private static final String RESPONSE = "response";
    private static final String OTHER_RESPONSE = "other response";
    private static final String ERROR = "error";
    private static final String OTHER_ERROR = "other error";

    private RestResponse<String, String> restResponse;

    @Test
    public void GetResponseOrElse_WhenResponseNonNull_ResponseIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .response(RESPONSE)
            .build();
        assertEquals(RESPONSE, restResponse.getResponseOrElse(OTHER_RESPONSE));
    }

    @Test
    public void GetResponseOrElse_WhenResponseIsNull_OtherIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .response(null)
            .build();
        assertEquals(OTHER_RESPONSE, restResponse.getErrorOrElse(OTHER_RESPONSE));
    }

    @Test
    public void GetResponseOrElseThrow_WhenResponseNonNull_ResponseIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .response(RESPONSE)
            .build();
        try {
            assertEquals(RESPONSE, restResponse.getResponseOrElseThrow());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void GetResponseOrElseThrow_WhenResponseIsNull_ExceptionIsThrown() {
        restResponse = RestResponse.<String, String>builder()
            .response(null)
            .build();
        assertThrows(RestClientException.class, () -> restResponse.getErrorOrElseThrow());
    }

    @Test
    public void GetResponseOrElseThrowWithSupplier_WhenResponseNonNull_ResponseIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .response(RESPONSE)
            .build();
        assertEquals(RESPONSE, restResponse.getResponseOrElseThrow(IllegalArgumentException::new));
    }

    @Test
    public void GetResponseOrElseThrowWithSupplier_WhenResponseIsNull_CustomExceptionIsThrown() {
        restResponse = RestResponse.<String, String>builder()
            .response(null)
            .build();
        assertThrows(IllegalArgumentException.class, () -> restResponse.getErrorOrElseThrow(IllegalArgumentException::new));
    }

    @Test
    public void GetErrorOrElse_WhenErrorNonNull_ErrorIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .error(ERROR)
            .build();
        assertEquals(ERROR, restResponse.getErrorOrElse(ERROR));
    }

    @Test
    public void GetErrorOrElse_WhenErrorIsNull_OtherIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .error(null)
            .build();
        assertEquals(OTHER_ERROR, restResponse.getErrorOrElse(OTHER_ERROR));
    }

    @Test
    public void GetErrorOrElseThrow_WhenErrorNonNull_ResponseIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .error(ERROR)
            .build();
        assertEquals(ERROR, restResponse.getErrorOrElseThrow());
    }

    @Test
    public void GetErrorOrElseThrow_WhenErrorIsNull_ExceptionIsThrown() {
        restResponse = RestResponse.<String, String>builder()
            .error(null)
            .build();
        assertThrows(RestClientException.class, () -> restResponse.getErrorOrElseThrow());
    }

    @Test
    public void GetErrorOrElseThrowWithSupplier_WhenErrorNonNull_ResponseIsReturned() {
        restResponse = RestResponse.<String, String>builder()
            .error(ERROR)
            .build();
        assertEquals(ERROR, restResponse.getErrorOrElseThrow(IllegalArgumentException::new));
    }

    @Test
    public void GetErrorOrElseThrowWithSupplier_WhenErrorIsNull_CustomExceptionIsThrown() {
        restResponse = RestResponse.<String, String>builder()
            .error(null)
            .build();
        assertThrows(IllegalArgumentException.class, () -> restResponse.getErrorOrElseThrow(IllegalArgumentException::new));
    }

}