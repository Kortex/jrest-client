package com.ariskourt.restclient.utils;

import com.ariskourt.restclient.MockDataProvider;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomJsonBodyPublisherTest extends MockDataProvider {

    @Mock
    private ObjectWriter writer;

    private CustomJsonBodyPublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new CustomJsonBodyPublisher(writer);
    }

    @Test
    public void GetPublisher_WhenCalled_PublisherIsReturned() throws JsonProcessingException {
        when(writer.writeValueAsString(any(Payload.class))).thenReturn(new ObjectMapper().writeValueAsString(new Payload(DATA)));
        HttpRequest.BodyPublisher bodyPublisher = publisher.getPublisher(new Payload(DATA));
        assertNotNull(bodyPublisher);
    }

    @Test
    public void GetPublisher_WhenExceptionsIsThrown_ItGetsRethrown() throws JsonProcessingException {
        when(writer.writeValueAsString(any(Payload.class))).thenThrow(new JsonMappingException("Some error"));
        assertThrows(JsonProcessingException.class, () -> publisher.getPublisher(new Payload(DATA)));
    }

    @Test
    public void DefaultConstructor_WheCalled_VerifyCorrectInitialization() {
        assertNotNull(new CustomJsonBodyPublisher());
    }

}