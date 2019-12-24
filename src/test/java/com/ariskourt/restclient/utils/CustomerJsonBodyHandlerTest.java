package com.ariskourt.restclient.utils;

import com.ariskourt.restclient.MockDataProvider;
import com.ariskourt.restclient.resources.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerJsonBodyHandlerTest extends MockDataProvider {

    @Mock
    private InputStreamUnmarshaller unmarshaller;

    private CustomerJsonBodyHandler<TargetClass, ErrorClass> handler;
    private Supplier<RestResponse<TargetClass, ErrorClass>> supplier;

    @BeforeEach
    void setUp() {
        handler = new CustomerJsonBodyHandler<>(TargetClass.class, ErrorClass.class, unmarshaller);
        supplier = () -> RestResponse.<TargetClass, ErrorClass>builder()
            .response(createTarget(200, "Some Data"))
            .build();
    }

    @Test
    @DisplayName("Verify handler returns the deserialized response correctly")
    public void Apply_WhenUnmarshallerReturnsResponse_VerifyHandlingIsDoneOk() throws Exception {
        when(unmarshaller.unmarshal(any(InputStream.class), any(Class.class), any(Class.class), any(HttpResponse.ResponseInfo.class))).thenReturn(supplier);
        var response = handler.apply(createFor(200))
            .getBody()
            .toCompletableFuture()
            .get()
            .get();
        assertAll(
            () -> assertNotNull(response),
            () -> assertNotNull(response.getResponse())
        );
    }

    @Test
    @DisplayName("Verify correct default constructor call")
    public void DefaultConstructor_WhenCalled_CorrectInitialization() {
        assertNotNull(new CustomerJsonBodyHandler<>(TargetClass.class, ErrorClass.class));
    }


}