package com.ariskourt.restclient;

import com.ariskourt.restclient.resources.RestResponse;
import com.ariskourt.restclient.utils.CustomerJsonBodyHandler;
import com.ariskourt.restclient.utils.PerformanceTimer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestClientTest extends MockDataProvider {

    private static final

    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse<Supplier<RestResponse<TargetClass, ErrorClass>>> response;

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
        when(client.send(any(HttpRequest.class), any(CustomerJsonBodyHandler.class))).thenReturn(response);
        when(response.body()).thenReturn(supplier);
        when(supplier.get()).thenReturn(createResponse());
        when(response.statusCode()).thenReturn(200);

    }

    public RestResponse<TargetClass, ErrorClass> createResponse() {
        return RestResponse.<TargetClass, ErrorClass>builder()
            .response(createTarget(200, "Some Data"))
            .code(200)
            .isSuccess(true)
            .build();
    }

}