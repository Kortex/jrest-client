package com.ariskourt.restclient;

import com.ariskourt.restclient.exceptions.RestClientException;
import com.ariskourt.restclient.resources.RestResponse;
import com.ariskourt.restclient.utils.CustomJsonBodyHandler;
import com.ariskourt.restclient.utils.PerformanceTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestClientTest extends MockDataProvider {

    private static final String SOME_URL = "https://reqres.in/api/users/2";
    private static final String INVALID_URL = "http://finance.yahoo.com/q/h?s=^IXIC";

    @Mock
    private HttpClient client;

    @Mock
    private CompletableFuture<HttpResponse<RestResponse<TargetClass, ErrorClass>>> httpResponseCompletableFuture;

    @Mock
    private CompletableFuture<Supplier<RestResponse<TargetClass, ErrorClass>>> supplierCompletableFuture;

    @Mock
    private Supplier<RestResponse<TargetClass, ErrorClass>> supplier;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = new RestClient(client, new PerformanceTimer());
    }

    @Test
    @DisplayName("Verify correct singleton creation and return")
    public void GetInstance_WhenSingletonAccessorIsCalled_VerifyOnlyTheSameInstanceGetsReturned() {
        var client = RestClient.getInstance();
        assertSame(client, RestClient.getInstance());
    }

    @Test
    @DisplayName("When GET request is performed and client performs everything is returned OK")
    public void GET_WhenClientPerformsOk_ResponseIsReturned() throws Exception {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenReturn(supplier);
        when(supplier.get()).thenReturn(createResponse());
        RestResponse<TargetClass, ErrorClass> restResponse = restClient.GET(SOME_URL, TargetClass.class, ErrorClass.class, Collections.emptyMap());
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(restResponse.getResponse()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(restResponse.getCode()),
            () -> assertTrue(restResponse.getSuccess()),
            () -> assertEquals(DATA, restResponse.getResponse().getData())
        );
    }

    @Test
    @DisplayName("When GET request is performed with an invalid URI, exception is thrown and handled")
    public void GET_WhenUriIsInvalid_URISyntaxExceptionIsThrownAndHandled() {
        assertThrows(RestClientException.class, () -> restClient.GET(INVALID_URL, TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When GET request is performed with an invalid URI, exception is thrown and handled")
    public void GET_WhenClientThrowsInterruptedException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new InterruptedException("Client got interrupted"));
        assertThrows(RestClientException.class, () -> restClient.GET(SOME_URL, TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When GET request is performed with an invalid URI, exception is thrown and handled")
    public void GET_WhenClientThrowsExecutionException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new ExecutionException(new IOException("Some I/O error")));
        assertThrows(RestClientException.class, () -> restClient.GET(SOME_URL, TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When POST request is performed and client performs OK, response is returned")
    public void POST_WhenClientPerformsOk_ResponseIsReturned() throws Exception {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenReturn(supplier);
        when(supplier.get()).thenReturn(createResponse());
        RestResponse<TargetClass, ErrorClass> restResponse = restClient.POST(SOME_URL, new Payload("Data"), TargetClass.class, ErrorClass.class, Collections.emptyMap());
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(restResponse.getResponse()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(restResponse.getCode()),
            () -> assertTrue(restResponse.getSuccess()),
            () -> assertEquals(DATA, restResponse.getResponse().getData())
        );
    }

    @Test
    @DisplayName("When POST request is performed with an invalid URI, exception is thrown and handled")
    public void POST_WhenUriIsInvalid_URISyntaxExceptionIsThrownAndHandled() {
        assertThrows(RestClientException.class, () -> restClient.POST(INVALID_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When POST request is performed with an invalid URI, exception is thrown and handled")
    public void POST_WhenClientThrowsInterruptedException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new InterruptedException("Client got interrupted"));
        assertThrows(RestClientException.class, () -> restClient.POST(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When POST request is performed with an invalid URI, exception is thrown and handled")
    public void POST_WhenClientThrowsExecutionException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new ExecutionException(new IOException("Some I/O error")));
        assertThrows(RestClientException.class, () -> restClient.POST(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PUT request is performed and client performs OK, response is returned")
    public void PUT_WhenClientPerformsOk_ResponseIsReturned() throws Exception {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenReturn(supplier);
        when(supplier.get()).thenReturn(createResponse());
        RestResponse<TargetClass, ErrorClass> restResponse = restClient.POST(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap());
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(restResponse.getResponse()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(restResponse.getCode()),
            () -> assertTrue(restResponse.getSuccess()),
            () -> assertEquals(DATA, restResponse.getResponse().getData())
        );
    }

    @Test
    @DisplayName("When PUT request is performed with an invalid URI, exception is thrown and handled")
    public void PUT_WhenUriIsInvalid_URISyntaxExceptionIsThrownAndHandled() {
        assertThrows(RestClientException.class, () -> restClient.PUT(INVALID_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PUT request is performed with an invalid URI, exception is thrown and handled")
    public void PUT_WhenClientThrowsInterruptedException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new InterruptedException("Client got interrupted"));
        assertThrows(RestClientException.class, () -> restClient.PUT(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PUT request is performed with an invalid URI, exception is thrown and handled")
    public void PUT_WhenClientThrowsExecutionException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new ExecutionException(new IOException("Some I/O error")));
        assertThrows(RestClientException.class, () -> restClient.PUT(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When DELETE request is performed and client performs OK, response is returned")
    public void DELETE_WhenClientPerformsOk_ResponseIsReturned() throws Exception {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenReturn(supplier);
        when(supplier.get()).thenReturn(createResponse());
        RestResponse<TargetClass, ErrorClass> restResponse = restClient.DELETE(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap());
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(restResponse.getResponse()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(restResponse.getCode()),
            () -> assertTrue(restResponse.getSuccess()),
            () -> assertEquals(DATA, restResponse.getResponse().getData())
        );
    }

    @Test
    @DisplayName("When DELETE request is performed with an invalid URI, exception is thrown and handled")
    public void DELETE_WhenUriIsInvalid_URISyntaxExceptionIsThrownAndHandled() {
        assertThrows(RestClientException.class, () -> restClient.DELETE(INVALID_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PUT request is performed with an invalid URI, exception is thrown and handled")
    public void DELETE_WhenClientThrowsInterruptedException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new InterruptedException("Client got interrupted"));
        assertThrows(RestClientException.class, () -> restClient.DELETE(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PUT request is performed with an invalid URI, exception is thrown and handled")
    public void DELETE_WhenClientThrowsExecutionException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new ExecutionException(new IOException("Some I/O error")));
        assertThrows(RestClientException.class, () -> restClient.DELETE(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PATCH request is performed and client performs OK, response is returned")
    public void PATCH_WhenClientPerformsOk_ResponseIsReturned() throws Exception {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenReturn(supplier);
        when(supplier.get()).thenReturn(createResponse());
        RestResponse<TargetClass, ErrorClass> restResponse = restClient.PATCH(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap());
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(restResponse.getResponse()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(restResponse.getCode()),
            () -> assertTrue(restResponse.getSuccess()),
            () -> assertEquals(DATA, restResponse.getResponse().getData())
        );
    }

    @Test
    @DisplayName("When PATCH request is performed with an invalid URI, exception is thrown and handled")
    public void PATCH_WhenUriIsInvalid_URISyntaxExceptionIsThrownAndHandled() {
        assertThrows(RestClientException.class, () -> restClient.PATCH(INVALID_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PUT request is performed with an invalid URI, exception is thrown and handled")
    public void PATCH_WhenClientThrowsInterruptedException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new InterruptedException("Client got interrupted"));
        assertThrows(RestClientException.class, () -> restClient.PATCH(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

    @Test
    @DisplayName("When PATCH request is performed with an invalid URI, exception is thrown and handled")
    public void PATCH_WhenClientThrowsExecutionException_ExceptionIsCaughtHandled() throws ExecutionException, InterruptedException {
        when(client.sendAsync(any(HttpRequest.class), any(CustomJsonBodyHandler.class))).thenReturn(httpResponseCompletableFuture);
        when(httpResponseCompletableFuture.thenApply(any(Function.class))).thenReturn(supplierCompletableFuture);
        when(supplierCompletableFuture.get()).thenThrow(new ExecutionException(new IOException("Some I/O error")));
        assertThrows(RestClientException.class, () -> restClient.PATCH(SOME_URL, new Payload(DATA), TargetClass.class, ErrorClass.class, Collections.emptyMap()));
    }

}