package com.ariskourt.restclient.utils;

import com.ariskourt.restclient.MockDataProvider;
import com.ariskourt.restclient.exceptions.UnmarshallerException;
import com.ariskourt.restclient.resources.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputStreamUnmarshallerTest extends MockDataProvider {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private InputStream stream;

    private InputStreamUnmarshaller unmarshaller;

    @BeforeEach
    void setUp() {
	unmarshaller = new InputStreamUnmarshaller(mapper);
    }

    @Test
    @DisplayName("When code is 200 unmarshal to target class")
    public void Unmarshal_WhenCodeIs200_DeserializeToTarget() throws IOException {
        var info = createFor(200);
	when(mapper.readValue(any(InputStream.class), any(Class.class))).thenReturn(createTarget(200, MockDataProvider.TARGET_MESSAGE));
	RestResponse<TargetClass, ErrorClass> response = unmarshaller.unmarshal(stream, TargetClass.class, ErrorClass.class, info).get();
	assertAll(
	    () -> assertNotNull(response),
	    () -> assertNotNull(response.getResponse()),
	    () -> assertEquals(info.statusCode(), (int) response.getCode()),
	    () -> assertNull(response.getError()),
	    () -> assertThat(response.getResponse()).isInstanceOf(TargetClass.class)
	);
    }

    @Test
    @DisplayName("When code is 207 unmarshal to target class")
    public void Unmarshal_WhenCodeIs207_DeserializeToTarget() throws IOException {
	var info = createFor(207);
	when(mapper.readValue(any(InputStream.class), any(Class.class))).thenReturn(createTarget(200, MockDataProvider.TARGET_MESSAGE));
	RestResponse<TargetClass, ErrorClass> response = unmarshaller.unmarshal(stream, TargetClass.class, ErrorClass.class, info).get();
	assertAll(
	    () -> assertNotNull(response),
	    () -> assertNotNull(response.getResponse()),
	    () -> assertEquals(info.statusCode(), (int) response.getCode()),
	    () -> assertNull(response.getError()),
	    () -> assertThat(response.getResponse()).isInstanceOf(TargetClass.class)
	);
    }

    @Test
    @DisplayName("When code is under 2xx unmarshal to error class")
    public void Unmarshal_WhenCodeIsUnder2xx_DeserializeToError() throws IOException {
        var info = createFor(0);
	when(mapper.readValue(any(InputStream.class), any(Class.class))).thenReturn(createError(100, MockDataProvider.ERROR_MESSAGE));
	RestResponse<TargetClass, ErrorClass> response = unmarshaller.unmarshal(stream, TargetClass.class, ErrorClass.class, info).get();
	assertAll(
	    () -> assertNotNull(response),
	    () -> assertNull(response.getResponse()),
	    () -> assertEquals(info.statusCode(), (int) response.getCode()),
	    () -> assertNotNull(response.getError()),
	    () -> assertThat(response.getError()).isInstanceOf(ErrorClass.class)
	);
    }

    @Test
    @DisplayName("When code is over 2xx unmarshal to error class")
    public void Unmarshal_WhenCodeIsOver2xx_DeserializeToError() throws IOException {
	var info = createFor(404);
	when(mapper.readValue(any(InputStream.class), any(Class.class))).thenReturn(createError(404, MockDataProvider.ERROR_MESSAGE));
	RestResponse<TargetClass, ErrorClass> response = unmarshaller.unmarshal(stream, TargetClass.class, ErrorClass.class, info).get();
	assertAll(
	    () -> assertNotNull(response),
	    () -> assertNull(response.getResponse()),
	    () -> assertEquals(info.statusCode(), (int) response.getCode()),
	    () -> assertNotNull(response.getError()),
	    () -> assertThat(response.getError()).isInstanceOf(ErrorClass.class)
	);
    }

    @Test
    @DisplayName("When unmarshalling for 2xx and mapper throws exception, it is handled correctly")
    public void Unmarshal_For2xxWhenMapperThrowsException_ExceptionIsCaughtAndReThrown() throws IOException {
	var info = createFor(200);
	when(mapper.readValue(any(InputStream.class), any(Class.class))).thenThrow(new IOException("Reading data has failed"));
	assertThrows(UnmarshallerException.class, () -> unmarshaller.unmarshal(stream, TargetClass.class, ErrorClass.class, info).get());
    }

    @Test
    @DisplayName("When unmarshalling for 2xx and mapper throws exception, it is handled correctly")
    public void Unmarshal_For4xxWhenMapperThrowsException_ExceptionIsCaughtAndReThrown() throws IOException {
	var info = createFor(404);
	when(mapper.readValue(any(InputStream.class), any(Class.class))).thenThrow(new IOException("Reading data has failed"));
	assertThrows(UnmarshallerException.class, () -> unmarshaller.unmarshal(stream, TargetClass.class, ErrorClass.class, info).get());
    }

    @Test
    @DisplayName("Verify correct default constructor call")
    public void DefaultConstructor_WhenCalled_CorrectInitialization() {
        assertNotNull(new InputStreamUnmarshaller());
    }

}